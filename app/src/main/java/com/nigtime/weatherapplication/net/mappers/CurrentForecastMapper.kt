/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.mappers

import com.nigtime.weatherapplication.domain.forecast.CurrentForecast
import com.nigtime.weatherapplication.domain.forecast.WeatherInfoHelper
import com.nigtime.weatherapplication.domain.forecast.Wind
import com.nigtime.weatherapplication.net.data.NetData
import com.nigtime.weatherapplication.net.json.JsonCurrentData
import com.nigtime.weatherapplication.net.json.JsonCurrentForecast

//TODO потокобезопастен ли этот маппер ?
class CurrentForecastMapper {

    fun map(json: NetData<JsonCurrentForecast>): CurrentForecast {
        val jsonCurrentData = json.data.forecastList[0]
        val detailedWeather = jsonCurrentData.toDetailedWeather()
        val wind = jsonCurrentData.toWind()
        return CurrentForecast(
            detailedWeather,
            wind,
            jsonCurrentData.averageHumidity,
            jsonCurrentData.pressure
        )
    }


    private fun JsonCurrentData.toWind(): Wind {
        return Wind(windSped, windDirectionDegrees)
    }

    private fun JsonCurrentData.toDetailedWeather(): CurrentForecast.DetailedWeather {
        val ico = WeatherInfoHelper.getIconByCode(weather.code)
        val temp = temp
        val feelsLikeTemp = feelsLikeTemp
        val description = WeatherInfoHelper.getDescriptionByCode(weather.code)
        return CurrentForecast.DetailedWeather(temp, ico, feelsLikeTemp, description)
    }

}