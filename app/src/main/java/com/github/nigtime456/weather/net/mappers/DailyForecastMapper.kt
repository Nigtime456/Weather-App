/*
 * Сreated by Igor Pokrovsky. 2020/6/13
 */

package com.github.nigtime456.weather.net.mappers

import com.github.nigtime456.weather.data.forecast.DailyForecast
import com.github.nigtime456.weather.net.json.DailyForecastJson
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DailyForecastMapper @Inject constructor() {

    fun map(dailyForecastJson: DailyForecastJson, timeZone: String): List<DailyForecast> {
        return dailyForecastJson.forecast.mapIndexed { index: Int, data: DailyForecastJson.Data ->
            DailyForecast(
                timeZone,
                TimeUnit.SECONDS.toMillis(data.time),
                index,
                data.icon,
                data.temperatureHigh,
                data.temperatureLow,
                data.dewPoint,
                data.humidity,
                data.pressure,
                data.windSpeed,
                data.windGust,
                data.windBearing,
                data.cloudCover,
                data.uvIndex,
                data.visibility,
                data.moonPhase,
                TimeUnit.SECONDS.toMillis(data.sunriseTime),
                TimeUnit.SECONDS.toMillis(data.sunsetTime)
            )
        }
    }
}