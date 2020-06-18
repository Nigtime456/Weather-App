/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.github.nigtime456.weather.screen.currently.activity

import com.github.nigtime456.weather.data.repository.LocationsRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class CurrentlyActivityPresenter @Inject constructor(
    private val view: CurrentlyActivityContract.View,
    private val locationsRepository: LocationsRepository
) : CurrentlyActivityContract.Presenter {

    private val compositeDisposable = CompositeDisposable()
    private var currentPage = 0

    override fun stop() {
        compositeDisposable.clear()
    }

    override fun restoreState(state: CurrentlyActivityContract.State?) {
        state?.let {
            currentPage = it.page
        }
    }

    override fun getState(): CurrentlyActivityContract.State {
        return CurrentlyActivityContract.State(currentPage)
    }

    override fun loadLocations() {
        compositeDisposable += locationsRepository.getLocations()
            .subscribeBy { list ->
                view.showLocations(list)
                view.showNavItems(list)
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
        view.closeDrawer()
    }

    override fun clickNavigationButton() {
        view.openDrawer()
    }

    override fun clickAboutApp() {
        view.showAboutAppScreen()
        view.closeDrawer()
    }

    override fun clickEditLocations() {
        view.showLocationsScreen()
        view.closeDrawer()
    }

    override fun clickSettings() {
        view.showSettingsScreen()
        view.closeDrawer()
    }

    override fun clickAddCity() {
        view.showSearchScreen()
    }

    override fun onCityInserted(position: Int) {
        currentPage = position
    }

    override fun onCitySelected(position: Int) {
        currentPage = position
    }
}