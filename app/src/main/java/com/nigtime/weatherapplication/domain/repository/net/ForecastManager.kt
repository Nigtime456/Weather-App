/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.domain.repository.net

import com.nigtime.weatherapplication.domain.forecast.CurrentForecast
import com.nigtime.weatherapplication.domain.params.RequestParams
import io.reactivex.Single

interface ForecastManager {
    fun getCurrentForecast(params: RequestParams): Single<CurrentForecast>
}