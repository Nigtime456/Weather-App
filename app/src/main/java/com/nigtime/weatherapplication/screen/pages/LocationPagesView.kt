/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screen.pages

import com.nigtime.weatherapplication.domain.location.SavedLocation

interface LocationPagesView {
    fun showDrawer()

    fun notifyLoadEnd()

    fun submitListToPager(items: List<SavedLocation>)
    fun submitListToNavView(items: List<SavedLocation>)

    fun setCurrentPage(page: Int)
    fun setCurrentNavItem(index: Int)

    fun navigateToSavedLocationsScreen()
    fun navigateToSearchCityScreen()
    fun navigateToSettingsScreen()
}