/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screen.currentforecast

import androidx.annotation.DrawableRes
import com.nigtime.weatherapplication.domain.forecast.*
import com.nigtime.weatherapplication.screen.common.MvpView


interface CurrentForecastView : MvpView {
    fun setCityName(cityName: String)
    fun showLoadLayout()
    fun showErrorLayout()
    fun showErrorMessage()
    fun showMainLayout()
    fun setDetailedWeather(detailedWeather: CurrentForecast.DetailedWeather)
    fun setHourlyForecast(hourlyWeatherList: List<HourlyForecast.HourlyWeather>)
    fun setDailyForecast(dailyWeather: List<DailyForecast.DailyWeather>)
    fun selectDaysSwitchButton(buttonId: Int)
    fun setVerticalScroll(scrollY: Int)
    fun setWind(wind: Wind)
    fun setHumidity(humidity: Int)
    fun setPressure(pressure: Double)
    fun setPrecipitation(probabilityOfPrecipitation: HourlyForecast.ProbabilityOfPrecipitation)
    fun setVisibility(visibility: Double)
    fun setAirQuality(airQuality: AirQuality)
    fun setUvIndex(uvIndex: UvIndex)
    fun setClouds(clouds: Int)
    fun setLargeWeatherIcon(@DrawableRes ico: Int)
}