/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.gmail.nigtime456.weatherapplication.net.repository

import com.gmail.nigtime456.weatherapplication.domain.net.RequestParams
import com.gmail.nigtime456.weatherapplication.net.dto.NetData
import com.gmail.nigtime456.weatherapplication.net.json.JsonCurrentForecast
import com.gmail.nigtime456.weatherapplication.net.json.JsonDailyForecast
import com.gmail.nigtime456.weatherapplication.net.json.JsonHourlyForecast
import io.reactivex.Observable

interface ForecastSource {
    fun getCurrentForecast(requestParams: RequestParams): Observable<NetData<JsonCurrentForecast>>
    fun getHourlyForecast(requestParams: RequestParams): Observable<NetData<JsonHourlyForecast>>
    fun getDailyForecast(requestParams: RequestParams): Observable<NetData<JsonDailyForecast>>
}