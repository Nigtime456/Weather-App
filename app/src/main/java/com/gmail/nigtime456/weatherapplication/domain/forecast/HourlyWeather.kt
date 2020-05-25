/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

package com.gmail.nigtime456.weatherapplication.domain.forecast

import androidx.annotation.DrawableRes

data class HourlyWeather(
    val temp: Double,
    @DrawableRes val ico: Int,
    val dateTime: Long
)
