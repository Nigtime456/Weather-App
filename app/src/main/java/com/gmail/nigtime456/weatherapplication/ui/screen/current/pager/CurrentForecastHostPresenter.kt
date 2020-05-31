/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.current.pager

import android.os.Parcel
import android.os.Parcelable
import com.gmail.nigtime456.weatherapplication.domain.repository.LocationsRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class CurrentForecastHostPresenter @Inject constructor(
    private val view: CurrentForecastHostContract.View,
    private val locationsRepository: LocationsRepository
) :
    CurrentForecastHostContract.Presenter {

    private class State(private val page: Int) : CurrentForecastHostContract.State {

        constructor(source: Parcel) : this(
            source.readInt()
        )

        override fun getCurrentPage(): Int = page

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeInt(page)
        }

        companion object {

            @JvmField
            val CREATOR: Parcelable.Creator<State> = object : Parcelable.Creator<State> {
                override fun createFromParcel(source: Parcel): State = State(source)
                override fun newArray(size: Int): Array<State?> = arrayOfNulls(size)
            }

        }
    }

    private val compositeDisposable = CompositeDisposable()
    private var currentPage = 0

    override fun stop() {
        compositeDisposable.clear()
    }

    override fun applyState(state: CurrentForecastHostContract.State?) {
        state?.let {
            currentPage = it.getCurrentPage()
        }
    }

    override fun getState(): CurrentForecastHostContract.State {
        return State(currentPage)
    }

    override fun loadLocations() {
        compositeDisposable += locationsRepository.getLocations()
            .subscribeBy { list ->
                view.showPages(list)
                view.showNavView(list)
                view.setCurrentPage(currentPage)
                view.setCurrentNavItem(currentPage)
            }
    }

    override fun scrollPage(page: Int) {
        currentPage = page
        view.setCurrentNavItem(currentPage)
    }

    override fun clickNavItem(index: Int) {
        view.setCurrentPage(index)
    }

    override fun clickAboutApp() {
        view.showDialogAboutApp()
    }

    override fun clickChangeLocations() {
        view.showEditLocationsScreen()
    }

    override fun clickSettings() {
        view.showSettingsScreen()
    }

    override fun clickWeatherNotifications() {
        view.showWeatherNotificationScreen()
    }

    override fun onCityInserted(position: Int) {
        currentPage = position
    }

    override fun onCitySelected(position: Int) {
        currentPage = position
    }
}