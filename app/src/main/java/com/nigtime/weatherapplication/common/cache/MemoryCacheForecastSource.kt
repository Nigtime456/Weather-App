/*
 * Сreated by Igor Pokrovsky. 2020/5/5
 */

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
        Log.d("cache", "current put = $key")
        currentCache.put(key, data)
    }

    override fun storeHourlyData(key: Long, data: NetData<JsonHourlyForecast>) {
        Log.d("cache", "hourly put = $key")
        hourlyCache.put(key, data)
    }

    override fun storeDailyData(key: Long, data: NetData<JsonDailyForecast>) {
        Log.d("cache", "daily put = $key")
        dailyCache.put(key, data)
    }

    /**
     * При извлечение из карты удаляется элемент
     */
    override fun getCurrentData(key: Long): NetData<JsonCurrentForecast>? {
        Log.d("cache", "current get = $key")
        dumpMaps()
        return currentCache.remove(key)
    }

    override fun getHourlyData(key: Long): NetData<JsonHourlyForecast>? {
        Log.d("cache", "hourly get = $key")
        dumpMaps()
        return hourlyCache.remove(key)
    }

    override fun getDailyData(key: Long): NetData<JsonDailyForecast>? {
        Log.d("cache", "daily get = $key")
        dumpMaps()
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