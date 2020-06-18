/*
 * Сreated by Igor Pokrovsky. 2020/6/13
 */


package com.github.nigtime456.weather.data.forecast

data class CurrentlyForecast constructor(
    val timeMs: Long,
    val weatherCode: String,
    val temp: Double,
    val apparentTemp: Double,
    val dewPoint: Double,
    val humidity: Double,
    val pressure: Double,
    val windSpeed: Double,
    val windGust: Double,
    val windDegrees: Int,
    val cloudCoverage: Double,
    val uvIndex: Int,
    val visibility: Double
)