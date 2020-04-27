/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.ui.screens.pager

import com.nigtime.weatherapplication.db.data.CityForForecastData
import com.nigtime.weatherapplication.ui.screens.common.MvpView

interface CityPagerView : MvpView {
    fun submitList(items: List<CityForForecastData>)
}