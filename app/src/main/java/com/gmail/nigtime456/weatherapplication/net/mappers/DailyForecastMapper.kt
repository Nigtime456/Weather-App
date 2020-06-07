/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.gmail.nigtime456.weatherapplication.net.mappers

import com.gmail.nigtime456.weatherapplication.data.forecast.DailyForecast
import com.gmail.nigtime456.weatherapplication.net.dto.NetData
import com.gmail.nigtime456.weatherapplication.net.json.JsonDailyForecast
import javax.inject.Inject

class DailyForecastMapper @Inject constructor(
    private val dailyWeatherMapper: DailyWeatherMapper
) {

    fun map(json: NetData<JsonDailyForecast>): DailyForecast {
        val dailyWeatherList = json.data.forecastList.mapIndexed(dailyWeatherMapper::map)

        return DailyForecast(json.loadTimestamp, dailyWeatherList)
    }
}