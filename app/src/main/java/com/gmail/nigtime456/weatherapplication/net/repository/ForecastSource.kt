/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.gmail.nigtime456.weatherapplication.net.repository

import com.gmail.nigtime456.weatherapplication.domain.params.RequestParams
import com.gmail.nigtime456.weatherapplication.net.data.NetData
import com.gmail.nigtime456.weatherapplication.net.json.JsonCurrentForecast
import com.gmail.nigtime456.weatherapplication.net.json.JsonDailyForecast
import com.gmail.nigtime456.weatherapplication.net.json.JsonHourlyForecast
import io.reactivex.Observable

interface ForecastSource {
    fun getJsonCurrentForecast(requestParams: RequestParams): Observable<NetData<JsonCurrentForecast>>
    fun getJsonHourlyForecast(requestParams: RequestParams): Observable<NetData<JsonHourlyForecast>>
    fun getJsonDailyForecast(requestParams: RequestParams): Observable<NetData<JsonDailyForecast>>
}