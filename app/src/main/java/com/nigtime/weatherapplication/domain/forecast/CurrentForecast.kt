/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.domain.forecast

class CurrentForecast constructor(val weatherInfo: DetailedWeather) {

    class DetailedWeather(
        temp: Double,
        ico: Int,
        val feelsLikeTemp: Double,
        val description: String
    ) : Weather(temp, ico)
}