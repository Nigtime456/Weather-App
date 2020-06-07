/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.gmail.nigtime456.weatherapplication.data.forecast

import androidx.annotation.DrawableRes

data class DailyForecast(
    val loadTimestamp: Long,
    val weatherList: List<Weather>
) {

    class Weather(
        val maxTemp: Double,
        val minTemp: Double,
        @DrawableRes val ico: Int,
        val index: Int,
        val dateTime: Long
    )
}