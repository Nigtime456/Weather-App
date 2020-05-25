/*
 * Сreated by Igor Pokrovsky. 2020/5/5
 */


package com.gmail.nigtime456.weatherapplication.net.cache

import androidx.collection.LruCache
import com.gmail.nigtime456.weatherapplication.domain.params.RequestParams
import com.gmail.nigtime456.weatherapplication.net.data.NetData
import com.gmail.nigtime456.weatherapplication.net.json.JsonCurrentForecast
import com.gmail.nigtime456.weatherapplication.net.json.JsonDailyForecast
import com.gmail.nigtime456.weatherapplication.net.json.JsonHourlyForecast

/**
 * Хранит данные в памяти приложения.
 */
class MemoryCacheForecastSource {

    companion object {
        private const val MAX_CACHE_SIZE = 200
    }

    private val currentForecastCache: LruCache<Long, NetData<JsonCurrentForecast>> =
        LruCache(MAX_CACHE_SIZE)
    private val hourlyForecastCache: LruCache<Long, NetData<JsonHourlyForecast>> =
        LruCache(MAX_CACHE_SIZE)
    private val dailyForecastCache: LruCache<Long, NetData<JsonDailyForecast>> =
        LruCache(MAX_CACHE_SIZE)


    fun storeJsonCurrentForecast(requestParams: RequestParams, data: NetData<JsonCurrentForecast>) {
        currentForecastCache.put(requestParams.getKey(), data)
    }

    fun storeJsonHourlyForecast(requestParams: RequestParams, data: NetData<JsonHourlyForecast>) {
        hourlyForecastCache.put(requestParams.getKey(), data)
    }

    fun storeJsonDailyForecast(requestParams: RequestParams, data: NetData<JsonDailyForecast>) {
        dailyForecastCache.put(requestParams.getKey(), data)
    }


    fun getJsonCurrentForecast(requestParams: RequestParams): NetData<JsonCurrentForecast>? {
        return currentForecastCache.get(requestParams.getKey())
    }

    fun getJsonHourlyForecast(requestParams: RequestParams): NetData<JsonHourlyForecast>? {
        return hourlyForecastCache.get(requestParams.getKey())
    }

    fun getJsonDailyForecast(requestParams: RequestParams): NetData<JsonDailyForecast>? {
        return dailyForecastCache.get(requestParams.getKey())
    }
}