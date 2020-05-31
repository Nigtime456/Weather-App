/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.current.pager

import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.domain.repository.SavedLocationsRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject

class CurrentForecastHostPresenter @Inject constructor(
    private val view: CurrentForecastHostContract.View,
    private val savedLocationsRepository: SavedLocationsRepository
) :
    CurrentForecastHostContract.Presenter {

    private val compositeDisposable = CompositeDisposable()
    private var currentPage = 0

    override fun stop() {
        compositeDisposable.clear()
    }

    override fun loadLocations() {
        compositeDisposable += savedLocationsRepository.getLocations()
            .subscribe(this::onNextListLocations)
    }

    private fun onNextListLocations(list: List<SavedLocation>) {
        view.showPages(list)
        view.showNavView(list)
        view.setCurrentPage(currentPage)
        view.setCurrentNavItem(currentPage)
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
        view.showChangeLocationsScreen()
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
}