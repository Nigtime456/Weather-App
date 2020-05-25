/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.gmail.nigtime456.weatherapplication.net.mappers

import com.gmail.nigtime456.weatherapplication.domain.forecast.HourlyForecast
import com.gmail.nigtime456.weatherapplication.domain.util.WeatherConditionHelper
import com.gmail.nigtime456.weatherapplication.net.data.NetData
import com.gmail.nigtime456.weatherapplication.net.json.JsonHourlyData
import com.gmail.nigtime456.weatherapplication.net.json.JsonHourlyForecast
import java.text.SimpleDateFormat
import java.util.*


class HourlyForecastMapper {

    fun map(json: NetData<JsonHourlyForecast>): HourlyForecast {
        val hourlyWeatherList = json.data.forecastList.map { it.toHourlyForecast() }
        return HourlyForecast(hourlyWeatherList, json.data.toProbabilityOfPrecipitation())
    }

    private fun JsonHourlyForecast.toProbabilityOfPrecipitation(): HourlyForecast.ProbabilityOfPrecipitation {
        val next3Hours = getMaxPrecipitation(3)
        val next6Hours = getMaxPrecipitation(6)
        val next12Hours = getMaxPrecipitation(12)
        return HourlyForecast.ProbabilityOfPrecipitation(next3Hours, next6Hours, next12Hours)
    }

    private fun JsonHourlyForecast.getMaxPrecipitation(hours: Int): Int {
        return forecastList
            .take(hours)
            .maxBy { jsonHourlyData ->
                jsonHourlyData.probabilityOfPrecipitation
            }?.probabilityOfPrecipitation ?: 0
    }

    private fun JsonHourlyData.toHourlyForecast(): HourlyForecast.HourlyWeather {
        //"2020-02-22T21:00:00"
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ROOT)
        val ico = WeatherConditionHelper.getIconByCode(weather.icon)
        val unixHour = dateFormatter.parse(timeStamp)?.time ?: 0
        return HourlyForecast.HourlyWeather(temp, ico, unixHour)
    }
}