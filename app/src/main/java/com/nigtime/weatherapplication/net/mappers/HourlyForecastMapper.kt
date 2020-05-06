/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.mappers

import com.nigtime.weatherapplication.domain.common.NetData
import com.nigtime.weatherapplication.domain.forecast.HourlyForecast
import com.nigtime.weatherapplication.domain.forecast.WeatherInfoHelper
import com.nigtime.weatherapplication.net.json.JsonHourlyData
import com.nigtime.weatherapplication.net.json.JsonHourlyForecast

class HourlyForecastMapper {

    fun map(json: NetData<JsonHourlyForecast>): HourlyForecast {
        val hourlyWeatherList = json.data.forecastList.map { it.toHourlyForecast() }
        val hourlyForecast =  HourlyForecast(hourlyWeatherList)
        return hourlyForecast
    }

    fun JsonHourlyData.toHourlyForecast(): HourlyForecast.HourlyWeather {
        val ico = WeatherInfoHelper.getIconByCode(weather.code)
        //"2020-02-22T21:00:00" -> 21:00:00 -> 21:00
        val hour = timeStamp.takeLast(8).take(5)
        return HourlyForecast.HourlyWeather(temp, ico, hour)
    }
}