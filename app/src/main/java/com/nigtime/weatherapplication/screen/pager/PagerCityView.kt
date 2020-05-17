/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screen.pager

import com.nigtime.weatherapplication.domain.city.CityForForecast

interface PagerCityView {
    fun submitListToPager(items: List<CityForForecast>)
    fun submitListToNavView(items: List<CityForForecast>)
    fun setCurrentPage(page: Int)
    fun setCurrentNavItem(index: Int)
    fun navigateToWishListScreen()
    fun navigateToSearchCityScreen()
    fun navigateToSettingsScreen()
}