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
import com.nigtime.weatherapplication.utility.log.CustomLogger

class App : Application() {
    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        setLogger()
        initDatabase()
    }

    private fun initDatabase() {
        database = AppDatabase.get(this)
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