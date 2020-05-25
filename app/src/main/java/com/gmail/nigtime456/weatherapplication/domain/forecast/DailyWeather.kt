/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

package com.gmail.nigtime456.weatherapplication.domain.forecast

import androidx.annotation.DrawableRes

class DailyWeather(
    val maxTemp: Double,
    val minTemp: Double,
    @DrawableRes val ico: Int,
    val index: Int,
    val dateTime: Long
)