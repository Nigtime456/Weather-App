/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.repository

import android.util.Log
import com.nigtime.weatherapplication.common.App
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

//TODO кэширование должно происходить в отдельных потоках
class ForecastManagerImpl constructor(
    private val netSource: ForecastSource,
    private val memoryCacheSource: AbstractCacheForecastSource,
    private val currentDataMapper: CurrentForecastMapper,
    private val hourlyDataMapper: HourlyForecastMapper,
    private val dailyDataMapper: DailyForecastMapper
) : ForecastManager {

    override fun getCurrentForecast(
        params: RequestParams,
        forceNet: Boolean
    ): Observable<CurrentForecast> {
        return getCachedCurrentSource(params)
            .doOnNext { App.INSTANCE.logger.d("currentLoad") }
            .doOnNext { Log.d("cache", "doOnNext") }
            .doOnNext { data -> memoryCacheSource.cacheCurrentForecast(data, params) }
            .map(currentDataMapper::map)
    }

    private fun getCachedCurrentSource(params: RequestParams): Observable<NetData<JsonCurrentForecast>> {
        return memoryCacheSource.getJsonCurrentForecast(params)
            .doOnEach {
                Log.d(
                    "cache",
                    "cache [$params] data = ${it.value} , completed = ${it.isOnComplete}"
                )
            }
            .switchIfEmpty(netSource.getJsonCurrentForecast(params)
                .doOnEach {
                    Log.d(
                        "cache",
                        "net [$params] data = ${it.value} er = ${it.error}, completed = ${it.isOnComplete}"
                    )
                })
    }


    override fun getHourlyForecast(
        params: RequestParams,
        forceNet: Boolean
    ): Observable<HourlyForecast> {
        return memoryCacheSource.getJsonHourlyForecast(params)
            .switchIfEmpty(netSource.getJsonHourlyForecast(params))
            .doOnNext { memoryCacheSource.cacheHourlyForecast(it, params) }
            .doOnNext { App.INSTANCE.logger.d("hourlyLoad") }
            .map(hourlyDataMapper::map)
    }

    override fun getDailyForecast(
        params: RequestParams,
        forceNet: Boolean
    ): Observable<DailyForecast> {
        return memoryCacheSource.getJsonDailyForecast(params)
            .switchIfEmpty(netSource.getJsonDailyForecast(params))
            .doOnNext { memoryCacheSource.cacheDailyForecast(it, params) }
            .doOnNext { App.INSTANCE.logger.d("dailyLoad") }
            .map(dailyDataMapper::map)

    }
}
