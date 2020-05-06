/*
 * Сreated by Igor Pokrovsky. 2020/5/5
 */

package com.nigtime.weatherapplication.common.cache

import com.nigtime.weatherapplication.domain.common.NetData
import com.nigtime.weatherapplication.domain.param.RequestParams
import com.nigtime.weatherapplication.net.json.JsonCurrentForecast
import com.nigtime.weatherapplication.net.json.JsonDailyForecast
import com.nigtime.weatherapplication.net.json.JsonHourlyForecast
import com.nigtime.weatherapplication.net.repository.CacheForecastSource
import io.reactivex.Observable
import io.reactivex.ObservableEmitter


abstract class AbstractCacheForecastSource constructor(private val maxAge: Long) :
    CacheForecastSource {

    final override fun cacheCurrentForecast(
        data: NetData<JsonCurrentForecast>,
        requestParams: RequestParams
    ) {
        storeCurrentData(getKey(requestParams), data)
    }

    final override fun cacheHourlyForecast(
        data: NetData<JsonHourlyForecast>,
        requestParams: RequestParams
    ) {
        storeHourlyData(getKey(requestParams), data)
    }

    final override fun cacheDailyForecast(
        data: NetData<JsonDailyForecast>,
        requestParams: RequestParams
    ) {
        storeDailyData(getKey(requestParams), data)
    }

    final override fun getJsonCurrentForecast(requestParams: RequestParams): Observable<NetData<JsonCurrentForecast>> {
        return Observable.create { emitter ->
            setObservable(emitter, getCurrentData(getKey(requestParams)))
        }
    }

    final override fun getJsonHourlyForecast(requestParams: RequestParams): Observable<NetData<JsonHourlyForecast>> {
        return Observable.create { emitter ->
            setObservable(emitter, getHourlyData(getKey(requestParams)))
        }
    }

    final override fun getJsonDailyForecast(requestParams: RequestParams): Observable<NetData<JsonDailyForecast>> {
        return Observable.create { emitter ->
            setObservable(emitter, getDailyData(getKey(requestParams)))
        }
    }

    private fun <T> setObservable(emitter: ObservableEmitter<NetData<T>>, data: NetData<T>?) {
        if (data == null || !data.isDataUpToDate()) {
            emitter.onComplete()
        } else {
            emitter.onNext(data)
            emitter.onComplete()
        }

    }

    private fun NetData<*>.isDataUpToDate(): Boolean {
        return System.currentTimeMillis() - timestamp <= maxAge
    }

    private fun getKey(requestParams: RequestParams): Long {
        return when (requestParams) {
            is RequestParams.CityParams -> requestParams.cityId
        }
    }


    protected abstract fun storeCurrentData(key: Long, data: NetData<JsonCurrentForecast>)
    protected abstract fun storeHourlyData(key: Long, data: NetData<JsonHourlyForecast>)
    protected abstract fun storeDailyData(key: Long, data: NetData<JsonDailyForecast>)

    protected abstract fun getCurrentData(key: Long): NetData<JsonCurrentForecast>?
    protected abstract fun getHourlyData(key: Long): NetData<JsonHourlyForecast>?
    protected abstract fun getDailyData(key: Long): NetData<JsonDailyForecast>?

}