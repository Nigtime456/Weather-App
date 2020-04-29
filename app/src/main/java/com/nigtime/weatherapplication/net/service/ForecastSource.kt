/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.service

import com.nigtime.weatherapplication.domain.params.RequestParams
import com.nigtime.weatherapplication.net.jsons.JsonCurrentForecast
import com.nigtime.weatherapplication.net.jsons.JsonDailyForecast
import com.nigtime.weatherapplication.net.jsons.JsonHourlyForecast
import io.reactivex.Single

interface ForecastSource {
    fun getCurrentForecastJson(requestParams: RequestParams): Single<JsonCurrentForecast>
    fun getHourlyForecastJson(requestParams: RequestParams): Single<JsonHourlyForecast>
    fun getDailyForecastJson(requestParams: RequestParams): Single<JsonDailyForecast>
}