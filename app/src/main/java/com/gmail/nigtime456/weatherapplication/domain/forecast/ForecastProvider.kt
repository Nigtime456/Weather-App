/*
 * Сreated by Igor Pokrovsky. 2020/5/6
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.gmail.nigtime456.weatherapplication.domain.forecast

import com.gmail.nigtime456.weatherapplication.domain.params.RequestParams
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