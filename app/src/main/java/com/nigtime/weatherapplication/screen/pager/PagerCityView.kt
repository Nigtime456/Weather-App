/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screen.pager

import com.nigtime.weatherapplication.domain.city.CityForForecast
import com.nigtime.weatherapplication.screen.common.MvpView

interface PagerCityView : MvpView {
    fun submitPageList(items: List<CityForForecast>)
    fun submitNavigationList(items: List<Pair<Int,String>>)
    fun setPage(page: Int, smoothScroll: Boolean)
    fun setNavigationItem(index: Int)
    fun navigateToWishListScreen()
}