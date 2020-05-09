/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.mappers

import com.nigtime.weatherapplication.domain.forecast.HourlyForecast
import com.nigtime.weatherapplication.domain.forecast.WeatherInfoHelper
import com.nigtime.weatherapplication.net.data.NetData
import com.nigtime.weatherapplication.net.json.JsonHourlyData
import com.nigtime.weatherapplication.net.json.JsonHourlyForecast

//TODO потокобезопастен ли этот маппер ?
class HourlyForecastMapper {

    fun map(json: NetData<JsonHourlyForecast>): HourlyForecast {
        val hourlyWeatherList = json.data.forecastList.map { it.toHourlyForecast() }
        return HourlyForecast(hourlyWeatherList, json.data.toProbabilityOfPrecipitation())
    }

    fun JsonHourlyForecast.toProbabilityOfPrecipitation(): HourlyForecast.ProbabilityOfPrecipitation {
        val next3Hours = forecastList.take(3)
            .maxBy { jsonHourlyData -> jsonHourlyData.probabilityOfPrecipitation }!!.probabilityOfPrecipitation
        val next6Hours = forecastList.take(6)
            .maxBy { jsonHourlyData -> jsonHourlyData.probabilityOfPrecipitation }!!.probabilityOfPrecipitation
        val next12Hours = forecastList.take(12)
            .maxBy { jsonHourlyData -> jsonHourlyData.probabilityOfPrecipitation }!!.probabilityOfPrecipitation
        return HourlyForecast.ProbabilityOfPrecipitation(next3Hours, next6Hours, next12Hours)
    }

    fun JsonHourlyData.toHourlyForecast(): HourlyForecast.HourlyWeather {
        val ico = WeatherInfoHelper.getIconByCode(weather.code)
        //"2020-02-22T21:00:00" -> 21:00:00 -> 21:00
        val hour = timeStamp.takeLast(8).take(5)
        return HourlyForecast.HourlyWeather(temp, ico, hour)
    }
}