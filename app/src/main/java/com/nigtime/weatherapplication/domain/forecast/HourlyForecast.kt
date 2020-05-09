/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.domain.forecast

import androidx.annotation.DrawableRes

data class HourlyForecast constructor(val hourlyWeather: List<HourlyWeather>) {
    data class HourlyWeather(val temp: Double, @DrawableRes val ico: Int, val hour: String)
}