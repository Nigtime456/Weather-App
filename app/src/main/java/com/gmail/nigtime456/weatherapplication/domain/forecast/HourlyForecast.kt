/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.gmail.nigtime456.weatherapplication.domain.forecast

data class HourlyForecast(
    val loadTimestamp: Long,
    val weather: List<HourlyWeather>
)