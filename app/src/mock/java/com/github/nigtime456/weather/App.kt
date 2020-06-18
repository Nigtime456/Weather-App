/*
 * Сreated by Igor Pokrovsky. 2020/5/29
 */



package com.github.nigtime456.weather

import android.app.Application
import androidx.preference.PreferenceManager
import com.github.nigtime456.weather.di.AppComponent
import com.github.nigtime456.weather.di.ContextModule
import com.github.nigtime456.weather.di.DaggerAppComponent
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber

/**
 * Отладочная версия [Application]
 */

class App : Application() {

    companion object {
        private lateinit var appComponent: AppComponent
        fun getComponent(): AppComponent = appComponent
    }

    override fun onCreate() {
        super.onCreate()
        //TODO временное решение
        RxJavaPlugins.setErrorHandler {
            Timber.e(it, "RxJava: the error was not delivered")
        }
        setupPreferences()
        setDi()
        setLogger()
    }

    private fun setupPreferences() {
        PreferenceManager.setDefaultValues(this, R.xml.main_preferences, true)
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