/*
 * Сreated by Igor Pokrovsky. 2020/5/5
 */


package com.nigtime.weatherapplication.common.cache

import android.util.Log
import androidx.collection.LruCache
import com.nigtime.weatherapplication.net.data.NetData
import com.nigtime.weatherapplication.net.json.JsonCurrentForecast
import com.nigtime.weatherapplication.net.json.JsonDailyForecast
import com.nigtime.weatherapplication.net.json.JsonHourlyForecast
import com.nigtime.weatherapplication.net.repository.AbstractCacheForecastSource

/**
 * Хранит данные в памяти приложения.
 */
class MemoryCacheForecastSource : AbstractCacheForecastSource(CACHE_MAX_AGE) {

    companion object {
        //30 min
        private const val CACHE_MAX_AGE = 30 * 60 * 1000L
        private const val MAX_CACHE_SIZE = 100
    }

    private val currentCache: LruCache<Long, NetData<JsonCurrentForecast>> = LruCache(MAX_CACHE_SIZE)
    private val hourlyCache: LruCache<Long, NetData<JsonHourlyForecast>> = LruCache(MAX_CACHE_SIZE)
    private val dailyCache: LruCache<Long, NetData<JsonDailyForecast>> = LruCache(MAX_CACHE_SIZE)

    override fun storeCurrentData(key: Long, data: NetData<JsonCurrentForecast>) {
        currentCache.put(key, data)
    }

    override fun storeHourlyData(key: Long, data: NetData<JsonHourlyForecast>) {
        hourlyCache.put(key, data)
    }

    override fun storeDailyData(key: Long, data: NetData<JsonDailyForecast>) {
        dailyCache.put(key, data)
    }

    /**
     * При извлечение из карты удаляется элемент
     */
    override fun getAndRemoveCurrentData(key: Long): NetData<JsonCurrentForecast>? {
        return currentCache.remove(key)
    }

    override fun getAndRemoveHourlyData(key: Long): NetData<JsonHourlyForecast>? {
        return hourlyCache.remove(key)
    }

    override fun getAndRemoveDailyData(key: Long): NetData<JsonDailyForecast>? {
        return dailyCache.remove(key)
    }

    private fun dumpMaps() {
        Log.d("dump", "currentSize = ${currentCache.size()}")
        Log.d("dump", "hourlySize = ${hourlyCache.size()}")
        Log.d("dump", "dailySize = ${dailyCache.size()}")
        Log.d("dump", "current = $currentCache")
        Log.d("dump", "hourly = $hourlyCache")
        Log.d("dump", "daily = $dailyCache")
    }
}