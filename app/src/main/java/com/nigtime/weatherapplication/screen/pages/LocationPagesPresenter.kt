/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screen.pages

import com.nigtime.weatherapplication.domain.location.SavedLocation
import com.nigtime.weatherapplication.domain.location.SavedLocationsRepository
import com.nigtime.weatherapplication.screen.common.BasePresenter

class LocationPagesPresenter(private val savedLocationsRepository: SavedLocationsRepository) :
    BasePresenter<LocationPagesView>() {

    private var currentPage = 0

    override fun onAttach() {
        super.onAttach()
        provideLocations()
    }

    private fun provideLocations() {
        savedLocationsRepository.getLocations()
            .subscribe(this::onNextListLocations)
            .disposeOnDestroy()
    }

    private fun onNextListLocations(list: List<SavedLocation>) {
        getView()?.submitListToPager(list)
        getView()?.submitListToNavView(list)
        getView()?.setCurrentPage(currentPage)
        getView()?.setCurrentNavItem(currentPage)
        getView()?.notifyLoadEnd()
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