/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.repository

import android.util.Log
import com.nigtime.weatherapplication.domain.forecast.CurrentForecast
import com.nigtime.weatherapplication.domain.forecast.DailyForecast
import com.nigtime.weatherapplication.domain.forecast.ForecastManager
import com.nigtime.weatherapplication.domain.forecast.HourlyForecast
import com.nigtime.weatherapplication.domain.param.RequestParams
import com.nigtime.weatherapplication.net.data.NetData
import com.nigtime.weatherapplication.net.json.JsonCurrentForecast
import com.nigtime.weatherapplication.net.mappers.CurrentForecastMapper
import com.nigtime.weatherapplication.net.mappers.DailyForecastMapper
import com.nigtime.weatherapplication.net.mappers.HourlyForecastMapper
import io.reactivex.Observable

class ForecastManagerImpl constructor(
    private val netSource: ForecastSource,
    private val memoryCacheSource: CacheForecastSource,
    private val currentDataMapper: CurrentForecastMapper,
    private val hourlyDataMapper: HourlyForecastMapper,
    private val dailyDataMapper: DailyForecastMapper
) : ForecastManager {

    override fun getCurrentForecast(
        params: RequestParams,
        forceNet: Boolean
    ): Observable<CurrentForecast> {
        return getCachedCurrentSource(params)
            .doOnNext { Log.d("cache", "out next") }
            .doOnNext { memoryCacheSource.cacheCurrentForecast(it, params) }
            .map(currentDataMapper::map)
    }

    private fun getCachedCurrentSource(params: RequestParams): Observable<NetData<JsonCurrentForecast>> {
        return memoryCacheSource.getJsonCurrentForecast(params)
            .doOnEach { Log.d("cache", "cache = ${it.value} , completed = ${it.isOnComplete}") }
            .switchIfEmpty(netSource.getJsonCurrentForecast(params)
                .doOnEach {
                    Log.d("cache", "net result error = ${it.isOnError} er = ${it.error}")
                })
    }


    override fun getHourlyForecast(
        params: RequestParams,
        forceNet: Boolean
    ): Observable<HourlyForecast> {
        return memoryCacheSource.getJsonHourlyForecast(params)
            .switchIfEmpty(netSource.getJsonHourlyForecast(params))
            .doOnNext { memoryCacheSource.cacheHourlyForecast(it, params) }
            .map(hourlyDataMapper::map)
    }

    override fun getDailyForecast(
        params: RequestParams,
        forceNet: Boolean
    ): Observable<DailyForecast> {
        return memoryCacheSource.getJsonDailyForecast(params)
            .switchIfEmpty(netSource.getJsonDailyForecast(params))
            .doOnNext { memoryCacheSource.cacheDailyForecast(it, params) }
            .map(dailyDataMapper::map)

    }
}
