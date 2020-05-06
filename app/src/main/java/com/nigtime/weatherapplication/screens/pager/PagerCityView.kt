/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screens.pager

import com.nigtime.weatherapplication.domain.city.CityForForecast
import com.nigtime.weatherapplication.screens.common.MvpView

interface PagerCityView : MvpView {
    fun submitList(items: List<CityForForecast>)
    fun setPage(page: Int)
}