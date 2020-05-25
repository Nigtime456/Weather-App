/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.gmail.nigtime456.weatherapplication.domain.forecast

data class DailyForecast(
    val loadTimestamp: Long,
    val weather: List<DailyWeather>
)