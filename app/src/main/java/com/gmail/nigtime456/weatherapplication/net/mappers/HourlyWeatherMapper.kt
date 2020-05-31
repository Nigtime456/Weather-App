/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

package com.gmail.nigtime456.weatherapplication.net.mappers

import com.gmail.nigtime456.weatherapplication.domain.forecast.HourlyWeather
import com.gmail.nigtime456.weatherapplication.net.json.JsonHourlyData
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class HourlyWeatherMapper @Inject constructor() {

    private companion object {
        const val HOURLY_PATTERN = "yyyy-MM-dd'T'HH:mm"
    }

    fun map(jsonHourlyData: JsonHourlyData): HourlyWeather {
        //"2020-02-22T21:00:00"
        val dateFormatter = SimpleDateFormat(HOURLY_PATTERN, Locale.ROOT)
        val ico = WeatherConditionHelper.getIconByCode(jsonHourlyData.weather.icon)
        val unixHour = dateFormatter.parse(jsonHourlyData.timeStamp)?.time ?: 0

        return HourlyWeather(jsonHourlyData.temp, ico, unixHour)
    }
}