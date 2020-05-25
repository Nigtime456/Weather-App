/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.gmail.nigtime456.weatherapplication.net.mappers

import com.gmail.nigtime456.weatherapplication.domain.forecast.HourlyForecast
import com.gmail.nigtime456.weatherapplication.net.dto.NetData
import com.gmail.nigtime456.weatherapplication.net.json.JsonHourlyForecast


class HourlyForecastMapper constructor(
    private val hourlyWeatherMapper: HourlyWeatherMapper
) {

    fun map(json: NetData<JsonHourlyForecast>): HourlyForecast {
        val hourlyWeatherList = json.data.forecastList.map(hourlyWeatherMapper::map)
        return HourlyForecast(json.loadTimestamp, hourlyWeatherList)
    }
}