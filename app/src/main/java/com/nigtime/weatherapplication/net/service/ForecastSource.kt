/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.service

import com.nigtime.weatherapplication.domain.param.RequestParams
import com.nigtime.weatherapplication.net.json.JsonCurrentForecast
import com.nigtime.weatherapplication.net.json.JsonDailyForecast
import com.nigtime.weatherapplication.net.json.JsonHourlyForecast
import io.reactivex.Single

interface ForecastSource {
    fun getJsonCurrentForecast(requestParams: RequestParams): Single<JsonCurrentForecast>
    fun getJsonHourlyForecast(requestParams: RequestParams): Single<JsonHourlyForecast>
    fun getJsonDailyForecast(requestParams: RequestParams): Single<JsonDailyForecast>
}