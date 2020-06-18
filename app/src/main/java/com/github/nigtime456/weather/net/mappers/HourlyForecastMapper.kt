/*
 * Сreated by Igor Pokrovsky. 2020/6/13
 */

package com.github.nigtime456.weather.net.mappers

import com.github.nigtime456.weather.data.forecast.HourlyForecast
import com.github.nigtime456.weather.net.json.HourlyForecastJson
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HourlyForecastMapper @Inject constructor() {

    fun map(hourlyForecastJson: HourlyForecastJson): List<HourlyForecast> {
        return hourlyForecastJson.forecast.map { data ->
            HourlyForecast(
                TimeUnit.SECONDS.toMillis(data.time),
                data.icon,
                data.temperature
            )
        }
    }
}