/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.github.nigtime456.weather.net.repository

import com.github.nigtime456.weather.data.forecast.Forecast
import com.github.nigtime456.weather.data.location.SavedLocation
import com.github.nigtime456.weather.data.repository.ForecastProvider
import com.github.nigtime456.weather.net.dto.CachedData
import com.github.nigtime456.weather.net.mappers.ForecastMapper
import com.github.nigtime456.weather.utils.rx.SchedulerProvider
import io.reactivex.Observable
import javax.inject.Inject

class ForecastProviderImpl @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val mapper: ForecastMapper,
    private val source: ForecastSource
) : ForecastProvider {

    private val cache = mutableMapOf<Long, Observable<Forecast>>()

    override fun getForecast(
        location: SavedLocation,
        forceNet: Boolean
    ): Observable<Forecast> {
        return if (forceNet) {
            forceNet(location)
        } else {
            cache[location.id] ?: forceNet(location)
        }
    }

    private fun forceNet(location: SavedLocation): Observable<Forecast> {
        return Observable.fromCallable { source.getForecastAsJson(location) }
            .map(mapper::map)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .replay(1)
            .autoConnect()
            .also { observable -> cache[location.id] = observable }
    }

    //TODO remove it
    private fun createObservable(location: SavedLocation): Observable<CachedData> {
        return Observable.create { emitter ->
            try {
                if (emitter.isDisposed) {
                    return@create
                }
                emitter.onNext(source.getForecastAsJson(location))
                emitter.onComplete()
            } catch (t: Throwable) {
                //send exceptions only if there are still active subscribers
                if (!emitter.isDisposed) {
                    emitter.onError(t)
                }
            }
        }
    }
}
