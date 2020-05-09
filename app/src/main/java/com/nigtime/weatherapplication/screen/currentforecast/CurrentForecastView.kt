/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screen.currentforecast

import com.nigtime.weatherapplication.domain.forecast.CurrentForecast
import com.nigtime.weatherapplication.domain.forecast.DailyForecast
import com.nigtime.weatherapplication.domain.forecast.HourlyForecast
import com.nigtime.weatherapplication.domain.forecast.Wind
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
}