/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.gmail.nigtime456.weatherapplication.net.repository

import androidx.collection.LruCache
import com.gmail.nigtime456.weatherapplication.domain.forecast.CurrentForecast
import com.gmail.nigtime456.weatherapplication.domain.forecast.DailyForecast
import com.gmail.nigtime456.weatherapplication.domain.forecast.HourlyForecast
import com.gmail.nigtime456.weatherapplication.domain.net.RequestParams
import com.gmail.nigtime456.weatherapplication.domain.repository.ForecastProvider
import com.gmail.nigtime456.weatherapplication.net.mappers.CurrentForecastMapper
import com.gmail.nigtime456.weatherapplication.net.mappers.DailyForecastMapper
import com.gmail.nigtime456.weatherapplication.net.mappers.HourlyForecastMapper
import com.gmail.nigtime456.weatherapplication.tools.rx.SchedulerProvider
import io.reactivex.Observable
import javax.inject.Inject

class ForecastProviderImpl @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val netSource: ForecastSource,
    private val currentMapper: CurrentForecastMapper,
    private val hourlyMapper: HourlyForecastMapper,
    private val dailyMapper: DailyForecastMapper
) : ForecastProvider {

    private companion object {
        private const val MAX_CACHE_SIZE = 200
    }

    private val currentStore: LruCache<Long, Observable<CurrentForecast>> = LruCache(MAX_CACHE_SIZE)
    private val hourlyStore: LruCache<Long, Observable<HourlyForecast>> = LruCache(MAX_CACHE_SIZE)
    private val dailyStore: LruCache<Long, Observable<DailyForecast>> = LruCache(MAX_CACHE_SIZE)

    override fun getCurrentForecast(
        params: RequestParams,
        forceNet: Boolean
    ): Observable<CurrentForecast> {

        return if (forceNet) {
            forceNetCurrentForecast(params)
        } else {
            currentStore.get(params.getKey()) ?: forceNetCurrentForecast(params)
        }
    }

    private fun forceNetCurrentForecast(params: RequestParams): Observable<CurrentForecast> {
        return netSource.getCurrentForecast(params)
            .map(currentMapper::map)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .replay(1)
            .autoConnect()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .also { observable -> currentStore.put(params.getKey(), observable) }
    }

    override fun getHourlyForecast(
        params: RequestParams,
        forceNet: Boolean
    ): Observable<HourlyForecast> {
        return if (forceNet) {
            forceNetHourlyForecast(params)
        } else {
            hourlyStore.get(params.getKey()) ?: forceNetHourlyForecast(params)
        }
    }

    private fun forceNetHourlyForecast(params: RequestParams): Observable<HourlyForecast> {
        return netSource.getHourlyForecast(params)
            .map(hourlyMapper::map)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .replay(1)
            .autoConnect()
            .also { observable -> hourlyStore.put(params.getKey(), observable) }
    }

    override fun getDailyForecast(
        params: RequestParams,
        forceNet: Boolean
    ): Observable<DailyForecast> {
        return if (forceNet) {
            forceNetDailyForecast(params)
        } else {
            dailyStore.get(params.getKey()) ?: forceNetDailyForecast(params)
        }
    }

    private fun forceNetDailyForecast(params: RequestParams): Observable<DailyForecast> {
        return netSource.getDailyForecast(params)
            .map(dailyMapper::map)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .replay(1)
            .autoConnect()
            .also { observable -> dailyStore.put(params.getKey(), observable) }
    }
}
