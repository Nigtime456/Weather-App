/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.repository

import android.util.Log
import com.nigtime.weatherapplication.domain.common.NetData
import com.nigtime.weatherapplication.domain.param.RequestParams
import com.nigtime.weatherapplication.domain.repository.ForecastManager
import com.nigtime.weatherapplication.domain.weather.CurrentForecast
import com.nigtime.weatherapplication.domain.weather.DailyForecast
import com.nigtime.weatherapplication.domain.weather.HourlyForecast
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
            .doOnNext { Log.d("sas", "out next") }
            .doOnNext { memoryCacheSource.cacheCurrentForecast(it, params) }
            .map(currentDataMapper::map)
    }

    private fun getCachedCurrentSource(params: RequestParams): Observable<NetData<JsonCurrentForecast>> {
        return memoryCacheSource.getJsonCurrentForecast(params)
            .doOnEach { Log.d("sas", "cache = ${it.value} , completed = ${it.isOnComplete}") }
            .switchIfEmpty(netSource.getJsonCurrentForecast(params)
                .doOnEach {
                    Log.d("sas", "net result error = ${it.isOnError} er = ${it.error}")
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
