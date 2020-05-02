/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.mappers

import com.nigtime.weatherapplication.domain.weather.DailyForecast
import com.nigtime.weatherapplication.net.json.JsonDailyForecast

class DailyForecastMapper {

    fun map(json: JsonDailyForecast): DailyForecast {
        return DailyForecast()
    }

}