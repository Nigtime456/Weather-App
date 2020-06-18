/*
 * Сreated by Igor Pokrovsky. 2020/6/13
 */

package com.github.nigtime456.weather.net.mappers

import com.github.nigtime456.weather.data.forecast.Forecast
import com.github.nigtime456.weather.net.dto.CachedData
import com.github.nigtime456.weather.net.json.ForecastJson
import com.google.gson.Gson
import javax.inject.Inject

class ForecastMapper @Inject constructor(
    private val gson: Gson,
    private val currentlyForecastMapper: CurrentlyForecastMapper,
    private val hourlyForecastMapper: HourlyForecastMapper,
    private val dailyForecastMapper: DailyForecastMapper,
    private val partOfDayMapper: PartOfDayMapper
) {

    fun map(cachedData: CachedData): Forecast {
        val forecastJson = gson.fromJson(cachedData.data, ForecastJson::class.java)
        //get the part of day for today
        val forecastForToday = forecastJson.daily.forecast[0]

        return Forecast(
            cachedData.timestampMs,
            forecastJson.timezone,
            partOfDayMapper.map(forecastForToday),
            currentlyForecastMapper.map(forecastJson.currently),
            hourlyForecastMapper.map(forecastJson.hourly),
            dailyForecastMapper.map(forecastJson.daily, forecastJson.timezone)
        )
    }

}