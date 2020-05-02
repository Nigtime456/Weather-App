/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.domain.repository

import com.nigtime.weatherapplication.domain.weather.CurrentForecast
import com.nigtime.weatherapplication.domain.weather.DailyForecast
import com.nigtime.weatherapplication.domain.weather.HourlyForecast
import com.nigtime.weatherapplication.domain.param.RequestParams
import io.reactivex.Single

interface ForecastManager {
    fun getCurrentForecast(params: RequestParams): Single<CurrentForecast>

    fun getHourlyForecast(params: RequestParams): Single<HourlyForecast>

    fun getDailyForecast(params: RequestParams): Single<DailyForecast>
}