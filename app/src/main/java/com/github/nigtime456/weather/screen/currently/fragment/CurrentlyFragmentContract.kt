/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.github.nigtime456.weather.screen.currently.fragment

import com.github.nigtime456.weather.data.forecast.DailyForecast
import com.github.nigtime456.weather.data.forecast.HourlyForecast
import com.github.nigtime456.weather.data.forecast.PartOfDay
import com.github.nigtime456.weather.data.location.SavedLocation
import com.github.nigtime456.weather.data.settings.UnitOfLength
import com.github.nigtime456.weather.data.settings.UnitOfPressure
import com.github.nigtime456.weather.data.settings.UnitOfSpeed
import com.github.nigtime456.weather.data.settings.UnitOfTemp
import com.github.nigtime456.weather.screen.base.BasePresenter

interface CurrentlyFragmentContract {

    interface View {
        fun setLocationName(location: SavedLocation)

        fun showLoadLayout()
        fun showErrorLayout()
        fun showErrorMessage()
        fun showMainLayout()
        fun showDailyForecastScreen(location: SavedLocation, partOfDay: PartOfDay, dayIndex: Int)

        fun disableRefreshLayout()
        fun enableRefreshLayout()
        fun stopRefreshing(success: Boolean)
        fun setUpdateTime(time: Long)

        fun setPartOfDay(partOfDay: PartOfDay)
        fun setTimeZone(timeZone: String)

        fun showCurrentlyWeather(
            temp: Double,
            apparentTemp: Double,
            weatherCode: String,
            unitOfTemp: UnitOfTemp
        )

        fun showWind(
            degrees: Int,
            speed: Double,
            gust: Double,
            unitOfSpeed: UnitOfSpeed
        )

        fun showHumidity(humidity: Double)

        fun showUvIndex(index: Int)

        fun showPressure(
            pressure: Double,
            unitOfPressure: UnitOfPressure
        )

        fun showVisibility(
            visibility: Double,
            unitOfLength: UnitOfLength
        )

        fun showDewPoint(
            dewPoint: Double,
            unitOfTemp: UnitOfTemp
        )

        fun showCloudsCoverage(clouds: Double)

        fun showHourlyForecast(
            forecast: List<HourlyForecast>,
            unitOfTemp: UnitOfTemp,
            timeZone: String
        )

        fun showDailyForecast(forecast: List<DailyForecast>, unitOfTemp: UnitOfTemp)
    }

    interface Presenter : BasePresenter {
        fun provideForecast()
        fun clickDailyForecast(position: Int)
        fun onViewForeground()
        fun refreshData()
    }
}