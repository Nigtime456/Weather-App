/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.gmail.nigtime456.weatherapplication.net.mappers

import com.gmail.nigtime456.weatherapplication.data.forecast.CurrentForecast
import com.gmail.nigtime456.weatherapplication.net.dto.NetData
import com.gmail.nigtime456.weatherapplication.net.json.JsonCurrentData
import com.gmail.nigtime456.weatherapplication.net.json.JsonCurrentForecast
import javax.inject.Inject

class CurrentWeatherMapper @Inject constructor() {

    fun map(jsonCurrentData: JsonCurrentData): CurrentForecast.Weather {
        return CurrentForecast.Weather(
            jsonCurrentData.temp,
            jsonCurrentData.feelsLikeTemp,
            WeatherConditionHelper.getIconByCode(jsonCurrentData.weather.icon),
            WeatherConditionHelper.getDescriptionByCode(jsonCurrentData.weather.code)
        )
    }
}

class DetailedForecastMapper @Inject constructor(private val windMapper: WindMapper) {

    fun map(jsonCurrentData: JsonCurrentData): CurrentForecast.DetailedWeather {
        return CurrentForecast.DetailedWeather(
            windMapper.map(jsonCurrentData),
            jsonCurrentData.pressure,
            jsonCurrentData.visibility
        )
    }
}

class EnvironmentMapper @Inject constructor(
    private val airQualityMapper: AirQualityMapper,
    private val uvIndexMapper: UvIndexMapper
) {

    fun map(jsonCurrentData: JsonCurrentData): CurrentForecast.Environment {
        return CurrentForecast.Environment(
            jsonCurrentData.averageHumidity,
            airQualityMapper.map(jsonCurrentData),
            uvIndexMapper.map(jsonCurrentData),
            jsonCurrentData.cloudsCoverage,
            jsonCurrentData.dewPoint
        )
    }
}

class CurrentForecastMapper @Inject constructor(
    private val currentWeatherMapper: CurrentWeatherMapper,
    private val detailedForecastMapper: DetailedForecastMapper,
    private val environmentMapper: EnvironmentMapper
) {

    fun map(data: NetData<JsonCurrentForecast>): CurrentForecast {
        val jsonCurrentData = data.data.forecastList[0]

        return CurrentForecast(
            data.loadTimestamp,
            currentWeatherMapper.map(jsonCurrentData),
            detailedForecastMapper.map(jsonCurrentData),
            environmentMapper.map(jsonCurrentData),
            jsonCurrentData.timezone
        )
    }
}