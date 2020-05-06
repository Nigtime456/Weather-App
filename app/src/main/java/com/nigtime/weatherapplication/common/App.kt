/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/5
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.common

import android.app.Application
import com.nigtime.weatherapplication.common.cache.MemoryCacheForecastSource
import com.nigtime.weatherapplication.common.log.CustomLogger
import com.nigtime.weatherapplication.db.service.AppDatabase
import com.nigtime.weatherapplication.net.repository.CacheForecastSource
import com.nigtime.weatherapplication.net.service.ApiFactory

class App : Application() {
    lateinit var database: AppDatabase
        private set
    lateinit var apiFactory: ApiFactory
        private set
    lateinit var memoryCacheSource: CacheForecastSource

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        memoryCacheSource = MemoryCacheForecastSource()
        setLogger()
        initDatabase()
        initApiService()
    }

    private fun initDatabase() {
        database = AppDatabase.getInstance(this)
    }

    private fun initApiService() {
        apiFactory = ApiFactory.getInstance()
    }

    /**
     * Включить/отключить логирование
     */
    private fun setLogger() {
        CustomLogger.printLog(true)
    }

    companion object {
        lateinit var INSTANCE: App
    }
}