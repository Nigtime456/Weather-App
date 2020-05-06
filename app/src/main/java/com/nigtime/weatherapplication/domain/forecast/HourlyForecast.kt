/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.domain.forecast

class HourlyForecast constructor(val hourlyWeatherList: List<HourlyWeather>) {
    class HourlyWeather(temp: Double, ico: Int,val hour: String) : Weather(temp, ico)
}