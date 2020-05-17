/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screen.pages

import com.nigtime.weatherapplication.common.rx.SchedulerProvider
import com.nigtime.weatherapplication.domain.location.SavedLocation
import com.nigtime.weatherapplication.domain.location.SavedLocationsRepository
import com.nigtime.weatherapplication.screen.common.BasePresenter

class LocationPagesPresenter(
    schedulerProvider: SchedulerProvider,
    private val savedLocationsRepository: SavedLocationsRepository
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
            onListLoaded(retainedContainer.getAs(LIST_LOCATIONS))
        } else {
            provideLocations()
        }
    }

    /**
     * [SavedLocationsRepository.getLocationsAsFlowable] передает новые данные, как только они изменятся, поэтому
     * подписыватся нужно лишь раз.
     */
    private fun provideLocations() {
        savedLocationsRepository.getLocationsAsFlowable()
            .subscribeOn(schedulerProvider.syncDatabase())
            .observeOn(schedulerProvider.ui())
            .subscribeAndHandleError(onNext = this::onListLoaded)
    }

    private fun onListLoaded(list: List<SavedLocation>) {
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