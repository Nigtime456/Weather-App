/*
 * Сreated by Igor Pokrovsky. 2020/5/5
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/5
 */

package com.nigtime.weatherapplication.net.repository

import com.nigtime.weatherapplication.domain.common.NetData
import com.nigtime.weatherapplication.domain.param.RequestParams
import com.nigtime.weatherapplication.net.json.JsonCurrentForecast
import com.nigtime.weatherapplication.net.json.JsonDailyForecast
import com.nigtime.weatherapplication.net.json.JsonHourlyForecast

interface CacheForecastSource :
    ForecastSource {
    fun cacheCurrentForecast(data: NetData<JsonCurrentForecast>,requestParams: RequestParams)

    fun cacheHourlyForecast(data: NetData<JsonHourlyForecast>,requestParams: RequestParams)

    fun cacheDailyForecast(data: NetData<JsonDailyForecast>,requestParams: RequestParams)
}