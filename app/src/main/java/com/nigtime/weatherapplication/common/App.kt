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
import android.content.Context
import com.nigtime.weatherapplication.common.di.AppContainer
import com.nigtime.weatherapplication.common.log.CustomLogger

class App : Application() {
    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        appContainer = AppContainer(this)
        setLogger()
        getSharedPreferences("a", Context.MODE_PRIVATE)
            .registerOnSharedPreferenceChangeListener { sharedPreferences, key ->

            }
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