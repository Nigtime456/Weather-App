/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.ui.screens.pager

import com.nigtime.weatherapplication.domain.database.CityForForecast
import com.nigtime.weatherapplication.ui.screens.common.MvpView

interface PagerCityView : MvpView {
    fun submitList(items: List<CityForForecast>)
    fun setPage(page: Int)
}