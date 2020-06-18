/*
 * Сreated by Igor Pokrovsky. 2020/6/13
 */

package com.github.nigtime456.weather.net.mappers

import com.github.nigtime456.weather.data.forecast.CurrentlyForecast
import com.github.nigtime456.weather.net.json.CurrentlyForecastJson
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CurrentlyForecastMapper @Inject constructor() {

    fun map(currentlyForecastJson: CurrentlyForecastJson): CurrentlyForecast {
        return CurrentlyForecast(
            TimeUnit.SECONDS.toMillis(currentlyForecastJson.time),

            currentlyForecastJson.icon,
            currentlyForecastJson.temperature,
            currentlyForecastJson.apparentTemperature,
            currentlyForecastJson.dewPoint,
            currentlyForecastJson.humidity,
            currentlyForecastJson.pressure,
            currentlyForecastJson.windSpeed,
            currentlyForecastJson.windGust,
            currentlyForecastJson.windBearing,
            currentlyForecastJson.cloudCover,
            currentlyForecastJson.uvIndex,
            currentlyForecastJson.visibility
        )
    }
}