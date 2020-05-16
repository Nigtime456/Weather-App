/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.domain.forecast

import androidx.annotation.DrawableRes

data class HourlyForecast constructor(
    val hourlyWeather: List<HourlyWeather>,
    val probabilityOfPrecipitation: ProbabilityOfPrecipitation
) {

    data class HourlyWeather(
        val temp: Double,
        @DrawableRes val ico: Int,
        val hour: String
    )

    data class ProbabilityOfPrecipitation(
        val next3Hours: Int,
        val next6Hours: Int,
        val next12Hours: Int
    )
}