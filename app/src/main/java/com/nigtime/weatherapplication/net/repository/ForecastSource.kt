/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.repository

import com.nigtime.weatherapplication.net.data.NetData
import com.nigtime.weatherapplication.domain.param.RequestParams
import com.nigtime.weatherapplication.net.json.JsonCurrentForecast
import com.nigtime.weatherapplication.net.json.JsonDailyForecast
import com.nigtime.weatherapplication.net.json.JsonHourlyForecast
import io.reactivex.Observable

interface ForecastSource {
    fun getJsonCurrentForecast(requestParams: RequestParams): Observable<NetData<JsonCurrentForecast>>
    fun getJsonHourlyForecast(requestParams: RequestParams): Observable<NetData<JsonHourlyForecast>>
    fun getJsonDailyForecast(requestParams: RequestParams): Observable<NetData<JsonDailyForecast>>
}