/*
 * Сreated by Igor Pokrovsky. 2020/6/13
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.github.nigtime456.weather.data.forecast

data class HourlyForecast(
    val timeMs: Long,
    val iconCode: String,
    val temp: Double
)