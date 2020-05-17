/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screen.pages


import com.nigtime.weatherapplication.domain.location.ForecastLocation

interface LocationPagesView {
    fun showDrawer()

    fun submitListToPager(items: List<ForecastLocation>)
    fun submitListToNavView(items: List<ForecastLocation>)

    fun setCurrentPage(page: Int)
    fun setCurrentNavItem(index: Int)

    fun navigateToSavedLocationsScreen()
    fun navigateToSearchCityScreen()
    fun navigateToSettingsScreen()
}