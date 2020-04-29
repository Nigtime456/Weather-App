/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.mappers

import com.nigtime.weatherapplication.domain.forecast.CurrentForecast
import com.nigtime.weatherapplication.domain.forecast.DetailedForecast
import com.nigtime.weatherapplication.net.jsons.JsonCurrentData
import com.nigtime.weatherapplication.net.jsons.JsonCurrentForecast
import com.nigtime.weatherapplication.utility.Mapper
import com.nigtime.weatherapplication.utility.WeatherIconHelper

class CurrentForecastMapper : Mapper<JsonCurrentForecast, CurrentForecast> {
    override fun map(input: JsonCurrentForecast): CurrentForecast {
        val jsonCurrentData = input.forecastList[0]
        val detailedForecast = jsonCurrentData.toDetailedForecast()

        return CurrentForecast(detailedForecast)
    }


    private fun JsonCurrentData.toDetailedForecast(): DetailedForecast {
        val ico = WeatherIconHelper.getIconByCode(weather.code)
        val temp = temp
        val feelsLikeTemp = feelsLikeTemp
        val description = weather.description
        return DetailedForecast(temp, ico, description, feelsLikeTemp)
    }

}