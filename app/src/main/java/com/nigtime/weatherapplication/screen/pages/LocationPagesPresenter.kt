/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screen.pages

import com.nigtime.weatherapplication.common.rx.SchedulerProvider
import com.nigtime.weatherapplication.domain.location.ForecastLocation
import com.nigtime.weatherapplication.domain.location.ForecastLocationsRepository
import com.nigtime.weatherapplication.screen.common.BasePresenter

class LocationPagesPresenter(
    schedulerProvider: SchedulerProvider,
    private val forecastLocationsRepository: ForecastLocationsRepository
) : BasePresenter<LocationPagesView>(
    schedulerProvider
) {

    private companion object {
        const val LIST_LOCATIONS = "weatherapplication.list_locations"
    }

    private var currentPage = 0

    override fun onAttach() {
        super.onAttach()
        if (retainedContainer.contains(LIST_LOCATIONS)) {
            submitList(retainedContainer.getAs(LIST_LOCATIONS))
        } else {
            provideLocations()
        }
    }

    /**
     * [ForecastLocationsRepository] передает новые данные, как только они изменятся, поэтому
     * подписыватся нужно лишь раз.
     */
    private fun provideLocations() {
        forecastLocationsRepository.getLocations()
            .subscribeOn(schedulerProvider.syncDatabase())
            .observeOn(schedulerProvider.ui())
            .subscribeAndHandleError(onNext = this::submitList)
    }

    private fun submitList(list: List<ForecastLocation>) {
        retainedContainer.put(LIST_LOCATIONS, list)

        getView()?.submitListToPager(list)
        getView()?.submitListToNavView(list)
        getView()?.setCurrentPage(currentPage)
        getView()?.setCurrentNavItem(currentPage)
    }


    fun onChangeListLocationsClick() {
        getView()?.navigateToSavedLocationsScreen()
    }

    fun onNavigationItemClick(index: Int) {
        getView()?.setCurrentPage(index)
    }

    fun onPageScrolled(position: Int) {
        currentPage = position
        getView()?.setCurrentNavItem(currentPage)
    }

    fun onAddCityClick() {
        getView()?.navigateToSearchCityScreen()
    }

    fun onOpenDrawerClick() {
        getView()?.showDrawer()
    }

    fun onCityInserted(position: Int) {
        currentPage = position
    }

    fun onLocationSelected(position: Int) {
        currentPage = position
    }

    fun onSettingsClick() {
        getView()?.navigateToSettingsScreen()
    }


}