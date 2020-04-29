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
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication

import android.app.Application
import com.nigtime.weatherapplication.db.service.AppDatabase
import com.nigtime.weatherapplication.net.service.ApiService
import com.nigtime.weatherapplication.utility.log.CustomLogger

class App : Application() {
    lateinit var database: AppDatabase
        private set
    lateinit var apiService: ApiService
        private set

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        setLogger()
        initDatabase()
        initApiService()
    }

    private fun initDatabase() {
        database = AppDatabase.getInstance(this)
    }

    private fun initApiService() {
        apiService = ApiService.getInstance()
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