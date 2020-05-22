/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.repository

import com.nigtime.weatherapplication.common.rx.SchedulerProvider
import com.nigtime.weatherapplication.domain.forecast.CurrentForecast
import com.nigtime.weatherapplication.domain.forecast.DailyForecast
import com.nigtime.weatherapplication.domain.forecast.ForecastProvider
import com.nigtime.weatherapplication.domain.forecast.HourlyForecast
import com.nigtime.weatherapplication.domain.params.RequestParams
import com.nigtime.weatherapplication.net.cache.MemoryCacheForecastSource
import com.nigtime.weatherapplication.net.mappers.CurrentForecastMapper
import com.nigtime.weatherapplication.net.mappers.DailyForecastMapper
import com.nigtime.weatherapplication.net.mappers.HourlyForecastMapper
import io.reactivex.Observable

class ForecastProviderImpl constructor(
    private val schedulerProvider: SchedulerProvider,
    private val netSource: ForecastSource,
    private val memoryCacheSource: MemoryCacheForecastSource,
    private val currentDataMapper: CurrentForecastMapper,
    private val hourlyDataMapper: HourlyForecastMapper,
    private val dailyDataMapper: DailyForecastMapper
) : ForecastProvider {

    override fun getCurrentForecast(
        params: RequestParams,
        forceNet: Boolean
    ): Observable<CurrentForecast> {

        return resolveSource(
            params,
            forceNet,
            netSource::getJsonCurrentForecast,
            memoryCacheSource::getJsonCurrentForecast,
            memoryCacheSource::storeJsonCurrentForecast
        ).map(currentDataMapper::map)
    }

    override fun getHourlyForecast(
        params: RequestParams,
        forceNet: Boolean
    ): Observable<HourlyForecast> {
        return resolveSource(
            params,
            forceNet,
            netSource::getJsonHourlyForecast,
            memoryCacheSource::getJsonHourlyForecast,
            memoryCacheSource::storeJsonHourlyForecast
        ).map(hourlyDataMapper::map)
    }


    override fun getDailyForecast(
        params: RequestParams,
        forceNet: Boolean
    ): Observable<DailyForecast> {
        return resolveSource(
            params,
            forceNet,
            netSource::getJsonDailyForecast,
            memoryCacheSource::getJsonDailyForecast,
            memoryCacheSource::storeJsonDailyForecast
        ).map(dailyDataMapper::map)
    }

    private fun <T> resolveSource(
        params: RequestParams,
        forceNet: Boolean,
        netSource: (RequestParams) -> Observable<T>,
        memorySource: (RequestParams) -> T?,
        cacheMemory: (RequestParams, T) -> Unit
    ): Observable<T> {


        return if (forceNet) {
            setupNetStream(params, netSource(params), cacheMemory)
        } else {
            val memoryData = memorySource(params)
            if (memoryData != null) {
                Observable.just(memoryData)
            } else {
                setupNetStream(params, netSource(params), cacheMemory)
            }
        }
    }

    private fun <T> setupNetStream(
        params: RequestParams,
        netSource: Observable<T>,
        memoryCache: (RequestParams, T) -> Unit
    ): Observable<T> {
        return netSource
            .doOnNext { data -> memoryCache(params, data) }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }
}
