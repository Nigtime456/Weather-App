/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.gmail.nigtime456.weatherapplication.domain.forecast

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class CurrentForecast constructor(
    val loadTimestamp: Long,
    val temp: Double,
    @DrawableRes val ico: Int,
    val feelsLikeTemp: Double,
    @StringRes val description: Int,
    val wind: Wind,
    val humidity: Int,
    val pressure: Double,
    val visibility: Double,
    val airQuality: AirQuality,
    val uvIndex: UvIndex,
    val cloudsCoverage: Int,
    val timeZone: String,
    val sunInfo: SunInfo
)