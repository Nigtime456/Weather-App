/*
 * Сreated by Igor Pokrovsky. 2020/5/29
 */



package com.gmail.nigtime456.weatherapplication

import android.app.Application
import androidx.preference.PreferenceManager
import com.gmail.nigtime456.weatherapplication.di.AppComponent
import com.gmail.nigtime456.weatherapplication.di.DaggerAppComponent
import com.gmail.nigtime456.weatherapplication.di.other.ContextModule
import leakcanary.AppWatcher
import leakcanary.LeakCanary
import timber.log.Timber


/**
 * Отладочная версия [Application]
 * Включает [LeakCanary], логгер и прочее
 */

class App : Application() {

    companion object {
        private lateinit var appComponent: AppComponent
        fun getComponent(): AppComponent = appComponent
    }

    override fun onCreate() {
        super.onCreate()
        setupPreferences()
        setCanary()
        setDi()
        setLogger()
    }

    private fun setupPreferences() {
        PreferenceManager.setDefaultValues(this, R.xml.main_preferences, true)
        PreferenceManager.setDefaultValues(this, R.xml.data_preferences, true)
        PreferenceManager.setDefaultValues(this, R.xml.unit_preferences, true)
    }

    /**
     * Leak Canary отслеживает ЖЦ объектов и детектит утечки объектов
     *
     */
    private fun setCanary() {
        AppWatcher.config = AppWatcher.config.copy(
            enabled = true,
            watchActivities = true,
            watchFragments = true,
            watchFragmentViews = true,
            watchViewModels = true,
            watchDurationMillis = 5000
        )
        LeakCanary.config = LeakCanary.config.copy(retainedVisibleThreshold = 1)
    }

    private fun setDi() {
        appComponent = DaggerAppComponent.builder()
            .contextModule(ContextModule(this))
            .build()
    }

    /**
     * Включить/отключить логирование
     */
    private fun setLogger() {
        Timber.plant(Timber.DebugTree())
    }
}