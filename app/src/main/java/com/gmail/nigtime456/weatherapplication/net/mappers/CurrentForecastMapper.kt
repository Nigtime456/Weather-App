/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.gmail.nigtime456.weatherapplication.net.mappers

import com.gmail.nigtime456.weatherapplication.domain.forecast.CurrentForecast
import com.gmail.nigtime456.weatherapplication.net.dto.NetData
import com.gmail.nigtime456.weatherapplication.net.json.JsonCurrentForecast


class CurrentForecastMapper constructor(
    private val windMapper: WindMapper,
    private val airQualityMapper: AirQualityMapper,
    private val uvIndexMapper: UvIndexMapper,
    private val sunInfoMapper: SunInfoMapper
) {

    fun map(json: NetData<JsonCurrentForecast>): CurrentForecast {
        val jsonCurrentData = json.data.forecastList[0]

        val loadTimestamp = json.loadTimestamp

        val temp = jsonCurrentData.temp
        val feelsLikeTemp = jsonCurrentData.feelsLikeTemp
        val icon = WeatherConditionHelper.getIconByCode(jsonCurrentData.weather.icon)
        val description = WeatherConditionHelper.getDescriptionByCode(jsonCurrentData.weather.code)

        val wind = windMapper.map(jsonCurrentData)

        val humidity = jsonCurrentData.averageHumidity
        val pressure = jsonCurrentData.pressure
        val visibility = jsonCurrentData.visibility

        val airQuality = airQualityMapper.map(jsonCurrentData)
        val uvIndex = uvIndexMapper.map(jsonCurrentData)

        val cloudsCoverage = jsonCurrentData.cloudsCoverage

        val timeZone = jsonCurrentData.timezone

        val sunInfo = sunInfoMapper.map(jsonCurrentData)

        return CurrentForecast(
            loadTimestamp,
            temp,
            icon,
            feelsLikeTemp,
            description,
            wind,
            humidity,
            pressure,
            visibility,
            airQuality,
            uvIndex,
            cloudsCoverage,
            timeZone,
            sunInfo
        )
    }
}