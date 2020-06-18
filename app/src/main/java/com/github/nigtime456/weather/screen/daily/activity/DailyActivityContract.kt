/*
 * Сreated by Igor Pokrovsky. 2020/6/13
 */

package com.github.nigtime456.weather.screen.daily.activity

import com.github.nigtime456.weather.data.forecast.DailyForecast
import com.github.nigtime456.weather.data.location.SavedLocation
import com.github.nigtime456.weather.screen.base.BasePresenter

interface DailyActivityContract {

    interface View {
        fun showLocationName(location: SavedLocation)
        fun showDarkBackground()
        fun showLightBackground()
        fun showForecast(forecastList: List<DailyForecast>)
        fun showTabs(titles: List<String>)
        fun setPage(page: Int)

        fun stopRefreshing(success: Boolean)
        fun setUpdateTime(time: Long)

        fun showErrorMessage()

    }

    interface Presenter : BasePresenter {
        fun start()
        fun provideForecast()
        fun refreshData()
        fun scrollPage(page: Int)
    }
}