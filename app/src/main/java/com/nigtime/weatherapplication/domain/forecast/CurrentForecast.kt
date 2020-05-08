/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.domain.forecast

import androidx.annotation.StringRes

class CurrentForecast constructor(val weatherInfo: DetailedWeather) {
    //TODO он вообще нужен (как вложенный класс)
    class DetailedWeather(
        temp: Double,
        ico: Int,
        val feelsLikeTemp: Double,
        @StringRes val description: Int
    ) : Weather(temp, ico)
}