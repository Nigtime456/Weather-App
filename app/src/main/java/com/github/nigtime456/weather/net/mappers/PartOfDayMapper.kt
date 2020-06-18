/*
 * Сreated by Igor Pokrovsky. 2020/6/17
 */

package com.github.nigtime456.weather.net.mappers

import com.github.nigtime456.weather.data.forecast.PartOfDay
import com.github.nigtime456.weather.net.json.DailyForecastJson
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PartOfDayMapper @Inject constructor() {

    fun map(dailyForecast: DailyForecastJson.Data): PartOfDay {
        val sunrise = TimeUnit.SECONDS.toMillis(dailyForecast.sunriseTime)
        val sunset = TimeUnit.SECONDS.toMillis(dailyForecast.sunsetTime)
        val current = System.currentTimeMillis()
        val isDay = current in sunrise..sunset
        return if (isDay) {
            PartOfDay.DAY
        } else {
            PartOfDay.NIGHT
        }
    }
}