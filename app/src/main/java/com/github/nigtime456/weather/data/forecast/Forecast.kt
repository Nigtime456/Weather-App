/*
 * Сreated by Igor Pokrovsky. 2020/6/13
 */

package com.github.nigtime456.weather.data.forecast

data class Forecast(
    val timestampMs: Long,
    val timeZone: String,
    val partOfDay: PartOfDay,
    val currently: CurrentlyForecast,
    val hourly: List<HourlyForecast>,
    val daily: List<DailyForecast>
)