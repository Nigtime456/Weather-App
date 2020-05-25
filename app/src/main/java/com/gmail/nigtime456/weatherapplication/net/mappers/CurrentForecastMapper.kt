/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.gmail.nigtime456.weatherapplication.net.mappers

import com.gmail.nigtime456.weatherapplication.domain.forecast.AirQuality
import com.gmail.nigtime456.weatherapplication.domain.forecast.CurrentForecast
import com.gmail.nigtime456.weatherapplication.domain.forecast.UvIndex
import com.gmail.nigtime456.weatherapplication.domain.forecast.Wind
import com.gmail.nigtime456.weatherapplication.domain.util.WeatherConditionHelper
import com.gmail.nigtime456.weatherapplication.net.data.NetData
import com.gmail.nigtime456.weatherapplication.net.json.JsonCurrentForecast


class CurrentForecastMapper constructor(
    private val sunInfoMapper: SunInfoMapper
) {

    fun map(json: NetData<JsonCurrentForecast>): CurrentForecast {
        val jsonCurrentData = json.data.forecastList[0]
        val wind = Wind(jsonCurrentData.windSped, jsonCurrentData.windDirectionDegrees)
        val airQuality = AirQuality(jsonCurrentData.airQualityIndex)
        val uvIndex = UvIndex(jsonCurrentData.uvIndex.toInt())
        val icon = WeatherConditionHelper.getIconByCode(jsonCurrentData.weather.icon)
        val description = WeatherConditionHelper.getDescriptionByCode(jsonCurrentData.weather.code)
        val sunInfo = sunInfoMapper.map(jsonCurrentData)

        return CurrentForecast(
            jsonCurrentData.temp,
            icon,
            jsonCurrentData.feelsLikeTemp,
            description,
            wind,
            jsonCurrentData.averageHumidity,
            jsonCurrentData.pressure,
            jsonCurrentData.visibility,
            airQuality,
            uvIndex,
            jsonCurrentData.cloudsCoverage,
            jsonCurrentData.timezone,
            sunInfo
        )
    }


}