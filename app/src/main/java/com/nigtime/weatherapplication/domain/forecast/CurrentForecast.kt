/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.domain.forecast

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class CurrentForecast constructor(
    val detailedWeather: DetailedWeather,
    val wind: Wind,
    val humidity: Int,
    val pressure: Double
) {


    data class DetailedWeather(
        val temp: Double,
        @DrawableRes val ico: Int,
        val feelsLikeTemp: Double,
        @StringRes val description: Int
    )
}