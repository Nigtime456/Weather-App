/*
 * Сreated by Igor Pokrovsky. 2020/5/6
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.gmail.nigtime456.weatherapplication.data.repository

import com.gmail.nigtime456.weatherapplication.data.forecast.CurrentForecast
import com.gmail.nigtime456.weatherapplication.data.forecast.DailyForecast
import com.gmail.nigtime456.weatherapplication.data.forecast.HourlyForecast
import com.gmail.nigtime456.weatherapplication.data.net.RequestParams
import io.reactivex.Observable

/**
 * Репозиторий предоставляющий погоду
 */
interface ForecastProvider {

    /**
     * @param forceNet - игнорировать кэш.
     */
    fun getCurrentForecast(
        params: RequestParams,
        forceNet: Boolean = false
    ): Observable<CurrentForecast>

    fun getHourlyForecast(
        params: RequestParams,
        forceNet: Boolean = false
    ): Observable<HourlyForecast>

    fun getDailyForecast(
        params: RequestParams,
        forceNet: Boolean = false
    ): Observable<DailyForecast>
}