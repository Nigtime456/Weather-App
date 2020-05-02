/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.mappers

import com.nigtime.weatherapplication.domain.weather.CurrentForecast
import com.nigtime.weatherapplication.domain.weather.WeatherInfoHelper
import com.nigtime.weatherapplication.net.json.JsonCurrentData
import com.nigtime.weatherapplication.net.json.JsonCurrentForecast

class CurrentForecastMapper {

    fun map(json: JsonCurrentForecast): CurrentForecast {
        val jsonCurrentData = json.forecastList[0]
        val detailedForecast = jsonCurrentData.toDetailedForecast()
        return CurrentForecast(detailedForecast)
    }


    private fun JsonCurrentData.toDetailedForecast(): CurrentForecast.DetailedWeather {
        val ico = WeatherInfoHelper.getIconByCode(weather.code)
        val temp = temp
        val feelsLikeTemp = feelsLikeTemp
        val description = weather.description
        return CurrentForecast.DetailedWeather(temp, ico, feelsLikeTemp, description)
    }

}