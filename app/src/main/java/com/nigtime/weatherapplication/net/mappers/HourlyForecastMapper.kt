/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.mappers

import com.nigtime.weatherapplication.domain.weather.HourlyForecast
import com.nigtime.weatherapplication.domain.weather.WeatherInfoHelper
import com.nigtime.weatherapplication.net.json.JsonHourlyData
import com.nigtime.weatherapplication.net.json.JsonHourlyForecast

class HourlyForecastMapper {

    fun map(json: JsonHourlyForecast): HourlyForecast {
        val hourlyWeatherList = json.forecastList.map { it.toHourlyForecast() }
        return HourlyForecast(hourlyWeatherList)
    }

    fun JsonHourlyData.toHourlyForecast(): HourlyForecast.HourlyWeather {
        val ico = WeatherInfoHelper.getIconByCode(weather.code)
        //"2020-02-22T21:00:00" -> 21:00:00 -> 21:00
        val hour = timeStamp.takeLast(8).take(5)
        return HourlyForecast.HourlyWeather(temp, ico, hour)
    }
}