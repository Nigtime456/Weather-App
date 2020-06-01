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
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject


class CurrentForecastHostPresenter @Inject constructor(
    private val view: CurrentForecastHostContract.View,
    private val locationsRepository: LocationsRepository

) : CurrentForecastHostContract.Presenter {

    private class State(private val verticalScroll: Int, private val page: Int) :
        CurrentForecastHostContract.State {

        constructor(source: Parcel) : this(
            source.readInt(),
            source.readInt()
        )

        override fun describeContents() = 0

        override fun getCurrentPage(): Int = page

        override fun getVerticalScroll(): Int = verticalScroll

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeInt(verticalScroll)
            writeInt(page)
        }

        companion object {

            @JvmField
            @Suppress("unused")
            val CREATOR: Parcelable.Creator<State> = object : Parcelable.Creator<State> {
                override fun createFromParcel(source: Parcel): State = State(source)
                override fun newArray(size: Int): Array<State?> = arrayOfNulls(size)
            }
        }
    }

    private val compositeDisposable = CompositeDisposable()
    private val syncScrollSubject: Subject<Int> = BehaviorSubject.create()
    private var currentPage = 0

    override fun stop() {
        compositeDisposable.clear()
    }

    override fun restoreState(state: CurrentForecastHostContract.State?) {
        state?.let {
            syncScrollSubject.onNext(it.getVerticalScroll())
            currentPage = it.getCurrentPage()
        }
    }

    override fun getState(): CurrentForecastHostContract.State {
        return State(syncScrollSubject.blockingFirst(), currentPage)
    }

    override fun getSyncPageScrollSubject(): Subject<Int> {
        return syncScrollSubject
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