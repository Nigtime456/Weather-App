/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.gmail.nigtime456.weatherapplication.screen.current.fragment

import com.gmail.nigtime456.weatherapplication.data.forecast.CurrentForecast
import com.gmail.nigtime456.weatherapplication.data.forecast.DailyForecast
import com.gmail.nigtime456.weatherapplication.data.forecast.HourlyForecast
import com.gmail.nigtime456.weatherapplication.data.settings.UnitOfLength
import com.gmail.nigtime456.weatherapplication.data.settings.UnitOfPressure
import com.gmail.nigtime456.weatherapplication.data.settings.UnitOfSpeed
import com.gmail.nigtime456.weatherapplication.data.settings.UnitOfTemp
import com.gmail.nigtime456.weatherapplication.screen.base.BasePresenter

interface CurrentForecastFragmentContract {

    interface View {
        fun setLocationName(name: String)
        fun setTimezone(timeZone: String)

        fun showLoadLayout()
        fun showErrorLayout()
        fun showErrorMessage()
        fun showMainLayout()

        fun disableRefreshLayout()
        fun enableRefreshLayout()
        fun stopRefreshing()

        fun showCurrentWeather(
            position: Int,
            weather: CurrentForecast.Weather,
            unitOfTemp: UnitOfTemp
        )

        fun showHourlyWeather(
            position: Int,
            weatherList: List<HourlyForecast.Weather>,
            unitOfTemp: UnitOfTemp
        )

        fun showDailyWeather(
            position: Int,
            weatherList: List<DailyForecast.Weather>,
            unitOfTemp: UnitOfTemp
        )

        fun showDetailedWeather(
            position: Int,
            detailedWeather: CurrentForecast.DetailedWeather,
            unitOfSpeed: UnitOfSpeed,
            unitOfPressure: UnitOfPressure,
            unitOfLength: UnitOfLength
        )

        fun showEnvironment(position: Int, environment: CurrentForecast.Environment)
    }

    interface Presenter :
        BasePresenter<View> {
        fun provideForecast()
        fun refreshData()
    }
}