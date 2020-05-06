/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.domain.repository

import com.nigtime.weatherapplication.domain.param.RequestParams
import com.nigtime.weatherapplication.domain.weather.CurrentForecast
import com.nigtime.weatherapplication.domain.weather.DailyForecast
import com.nigtime.weatherapplication.domain.weather.HourlyForecast
import io.reactivex.Observable

interface ForecastManager {
    fun getCurrentForecast(params: RequestParams, forceNet: Boolean = false): Observable<CurrentForecast>

    fun getHourlyForecast(params: RequestParams, forceNet: Boolean = false): Observable<HourlyForecast>

    fun getDailyForecast(params: RequestParams, forceNet: Boolean = false): Observable<DailyForecast>
}