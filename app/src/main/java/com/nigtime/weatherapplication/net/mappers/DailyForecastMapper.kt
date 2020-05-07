/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.mappers

import com.nigtime.weatherapplication.net.data.NetData
import com.nigtime.weatherapplication.domain.forecast.DailyForecast
import com.nigtime.weatherapplication.net.json.JsonDailyForecast

class DailyForecastMapper {

    fun map(json: NetData<JsonDailyForecast>): DailyForecast {
        val dailyForecast = DailyForecast()
        return dailyForecast
    }

}