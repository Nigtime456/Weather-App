/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.ui.screens.currentforecast

import com.nigtime.weatherapplication.domain.weather.HourlyForecast
import com.nigtime.weatherapplication.ui.screens.common.MvpView

interface CurrentForecastView : MvpView {
    fun setCityName(cityName: String)
    fun showLoadAnimation()
    fun showErrorView()
    fun showErrorMessage()
    fun showMainLayout()
    fun setCurrentTemp(temp: String)
    fun setCurrentFeelsLikeTemp(temp: String)
    fun setCurrentDescription(description: String)
    fun setCurrentTempIco(ico: Int)
    fun showHourlyForecast(hourlyWeatherList: List<HourlyForecast.HourlyWeather>)
}