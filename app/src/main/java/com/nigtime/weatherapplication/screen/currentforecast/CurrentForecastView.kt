/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screen.currentforecast

import com.nigtime.weatherapplication.domain.forecast.HourlyForecast
import com.nigtime.weatherapplication.screen.common.MvpView

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