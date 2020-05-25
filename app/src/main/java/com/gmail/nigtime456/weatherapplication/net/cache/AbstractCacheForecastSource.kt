/*
 * Сreated by Igor Pokrovsky. 2020/5/8
 */

package com.gmail.nigtime456.weatherapplication.net.cache

import com.gmail.nigtime456.weatherapplication.domain.params.RequestParams
import com.gmail.nigtime456.weatherapplication.net.data.NetData
import com.gmail.nigtime456.weatherapplication.net.json.JsonCurrentForecast
import com.gmail.nigtime456.weatherapplication.net.json.JsonDailyForecast
import com.gmail.nigtime456.weatherapplication.net.json.JsonHourlyForecast
import io.reactivex.Observable
import io.reactivex.ObservableEmitter

/**
 * Базовый класс для кэширования.
 *
 * Подклассы должны просто реализовать методы сохранения и получения.
 * Подклассы сами задают максимальный срок хранения, базовый класс
 * оценит актуальность данных.
 *
 * Возвращает:
 * - [Observable] с данными, если они доступны и актуальны.
 * - Пустой (onCompleted()) [Observable] если данные не доступны или срок хранения истек.
 *
 * @param maxAgeMillis - максимальный срок хранения в миллисекундах.
 */
abstract class AbstractCacheForecastSource constructor(private val maxAgeMillis: Long) {

    fun cacheCurrentForecast(
        data: NetData<JsonCurrentForecast>,
        requestParams: RequestParams
    ) {
        storeCurrentData(getKey(requestParams), data)
    }

    fun cacheHourlyForecast(
        data: NetData<JsonHourlyForecast>,
        requestParams: RequestParams
    ) {
        storeHourlyData(getKey(requestParams), data)
    }

    fun cacheDailyForecast(
        data: NetData<JsonDailyForecast>,
        requestParams: RequestParams
    ) {
        storeDailyData(getKey(requestParams), data)
    }

    fun getJsonCurrentForecast(requestParams: RequestParams): Observable<NetData<JsonCurrentForecast>> {
        return Observable.create { emitter ->
            setObservable(emitter, getCurrentData(getKey(requestParams)))
        }
    }

    fun getJsonHourlyForecast(requestParams: RequestParams): Observable<NetData<JsonHourlyForecast>> {
        return Observable.create { emitter ->
            setObservable(emitter, getHourlyData(getKey(requestParams)))
        }
    }

    fun getJsonDailyForecast(requestParams: RequestParams): Observable<NetData<JsonDailyForecast>> {
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
        return System.currentTimeMillis() - timestamp <= maxAgeMillis
    }

    private fun getKey(requestParams: RequestParams): Long {
        return when (requestParams) {
            is RequestParams.City -> requestParams.cityId
        }
    }

    /**
     * Сохранить данные в источнике
     */
    protected abstract fun storeCurrentData(key: Long, data: NetData<JsonCurrentForecast>)
    protected abstract fun storeHourlyData(key: Long, data: NetData<JsonHourlyForecast>)
    protected abstract fun storeDailyData(key: Long, data: NetData<JsonDailyForecast>)

    /**
     * Вернуть данные из источника, если они есть
     *
     * @return null - if empty
     */
    protected abstract fun getCurrentData(key: Long): NetData<JsonCurrentForecast>?
    protected abstract fun getHourlyData(key: Long): NetData<JsonHourlyForecast>?
    protected abstract fun getDailyData(key: Long): NetData<JsonDailyForecast>?

}