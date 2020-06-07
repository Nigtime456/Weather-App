/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.gmail.nigtime456.weatherapplication.screen.current.activity

import com.gmail.nigtime456.weatherapplication.data.repository.LocationsRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class CurrentForecastActivityPresenter @Inject constructor(
    private val view: CurrentForecastActivityContract.View,
    private val locationsRepository: LocationsRepository
) : CurrentForecastActivityContract.Presenter {

    private val compositeDisposable = CompositeDisposable()
    private var currentPage = 0

    override fun stop() {
        compositeDisposable.clear()
    }

    override fun restoreState(state: CurrentForecastActivityContract.State?) {
        state?.let {
            currentPage = it.page
        }
    }

    override fun getState(): CurrentForecastActivityContract.State {
        return CurrentForecastActivityContract.State(currentPage)
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

    override fun clickEditLocations() {
        view.showLocationsScreen()
    }

    override fun clickSettings() {
        view.showSettingsScreen()
    }

    override fun clickWeatherNotifications() {
        view.showNotificationsScreen()
    }

    override fun onCityInserted(position: Int) {
        currentPage = position
    }

    override fun onCitySelected(position: Int) {
        currentPage = position
    }
}