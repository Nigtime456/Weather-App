/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.mappers

import android.annotation.SuppressLint
import com.nigtime.weatherapplication.domain.forecast.DailyForecast
import com.nigtime.weatherapplication.domain.forecast.WeatherInfoHelper
import com.nigtime.weatherapplication.net.data.NetData
import com.nigtime.weatherapplication.net.json.JsonDailyData
import com.nigtime.weatherapplication.net.json.JsonDailyForecast
import java.text.SimpleDateFormat

//TODO потокобезопастен ли этот маппер ?
class DailyForecastMapper {
    @SuppressLint("SimpleDateFormat")
    private val dateParser = SimpleDateFormat("yyyy-MM-dd")

    fun map(json: NetData<JsonDailyForecast>): DailyForecast {
        val dailyWeatherList = json.data.forecastList.mapIndexed { index, jsonDailyData ->
            jsonDailyData.toDailyWeather(index)
        }
        return DailyForecast(dailyWeatherList)
    }

    private fun JsonDailyData.toDailyWeather(index: Int): DailyForecast.DailyWeather {
        val ico = WeatherInfoHelper.getIconByCode(weather.code)
        val unixTime = dateParser.parse(date)?.time ?: 0
        return DailyForecast.DailyWeather(maxTemp, minTemp, ico, index, unixTime)
    }

}