/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.common

import android.app.Application
import com.nigtime.weatherapplication.utility.log.CustomLogger

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        setLogger()
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