/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.domain.forecast

class DailyForecast(val dailyWeather: List<DailyWeather>) {

    class DailyWeather(
        val maxTemp: Double,
        val minTemp: Double,
        val ico: Int,
        val index: Int,
        val unixTime: Long
    )
}