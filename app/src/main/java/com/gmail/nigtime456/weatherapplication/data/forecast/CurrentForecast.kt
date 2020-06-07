/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.gmail.nigtime456.weatherapplication.data.forecast

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class CurrentForecast constructor(
    val loadTimestamp: Long,
    val weather: Weather,
    val detailedWeather: DetailedWeather,
    val environment: Environment,
    val timeZone: String
) {

    data class Weather(
        val temp: Double,
        val feelsLikeTemp: Double,
        @DrawableRes val ico: Int,
        @StringRes val description: Int
    )

    data class DetailedWeather(
        val wind: Wind,
        val pressure: Double,
        val visibility: Double
    )

    data class Environment(
        val humidity: Int,
        val airQuality: AirQuality,
        val uvIndex: UvIndex,
        val cloudsCoverage: Int,
        val dewPoint: Double
    )

}