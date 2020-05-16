/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.mappers

import com.nigtime.weatherapplication.domain.forecast.AirQuality
import com.nigtime.weatherapplication.domain.forecast.CurrentForecast
import com.nigtime.weatherapplication.domain.forecast.UvIndex
import com.nigtime.weatherapplication.domain.forecast.Wind
import com.nigtime.weatherapplication.domain.utility.WeatherConditionHelper
import com.nigtime.weatherapplication.net.data.NetData
import com.nigtime.weatherapplication.net.json.JsonCurrentForecast


class CurrentForecastMapper constructor(
    private val sunInfoMapper: SunInfoMapper
) {

    fun map(json: NetData<JsonCurrentForecast>): CurrentForecast {
        val jsonCurrentData = json.data.forecastList[0]
        val wind = Wind(jsonCurrentData.windSped, jsonCurrentData.windDirectionDegrees)
        val airQuality = AirQuality(jsonCurrentData.airQualityIndex)
        val uvIndex = UvIndex(jsonCurrentData.uvIndex.toInt())
        val icon = WeatherConditionHelper.getIconByCode(jsonCurrentData.weather.code)
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