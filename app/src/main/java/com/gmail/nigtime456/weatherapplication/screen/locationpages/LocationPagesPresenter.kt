/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.gmail.nigtime456.weatherapplication.screen.locationpages

import android.util.Log
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocationsRepository
import com.gmail.nigtime456.weatherapplication.screen.common.BasePresenter

class LocationPagesPresenter(private val savedLocationsRepository: SavedLocationsRepository) :
    BasePresenter<LocationPagesView>() {

    private var currentPage = 0

    override fun onAttach() {
        super.onAttach()
        provideLocations()
    }

    private fun provideLocations() {
        startMeasure()
        savedLocationsRepository.getLocations()
            .subscribe(this::onNextListLocations)
            .disposeOnDestroy()
    }

    private fun onNextListLocations(list: List<SavedLocation>) {
        Log.d("sas", "LOAD LOCATIONS = [${endMeasure()}]")
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