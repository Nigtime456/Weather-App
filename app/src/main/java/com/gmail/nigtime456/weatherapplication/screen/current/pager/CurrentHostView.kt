/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.gmail.nigtime456.weatherapplication.screen.current.pager

import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation

interface CurrentHostView {
    fun showDrawer()

    fun submitListToPager(items: List<SavedLocation>)
    fun submitListToNavView(items: List<SavedLocation>)

    fun setCurrentPage(page: Int)
    fun setCurrentNavItem(index: Int)

    fun navigateToSavedLocationsScreen()
    fun navigateToSearchCityScreen()
    fun navigateToSettingsScreen()
}