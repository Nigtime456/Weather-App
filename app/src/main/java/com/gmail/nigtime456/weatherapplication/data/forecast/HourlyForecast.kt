/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.gmail.nigtime456.weatherapplication.data.forecast

import androidx.annotation.DrawableRes

data class HourlyForecast(
    val loadTimestamp: Long,
    val weatherList: List<Weather>
) {

    data class Weather(
        val temp: Double,
        @DrawableRes val ico: Int,
        val dateTime: Long
    )
}