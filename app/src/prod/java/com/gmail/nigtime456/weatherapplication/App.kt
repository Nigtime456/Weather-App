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

class App : Application() {

    companion object {
        private lateinit var appComponent: AppComponent
        fun getComponent(): AppComponent = appComponent
    }

    override fun onCreate() {
        super.onCreate()
        setupPreferences()
        setDI()
    }

    private fun setupPreferences() {
        PreferenceManager.setDefaultValues(this, R.xml.main_preferences, true)
        PreferenceManager.setDefaultValues(this, R.xml.data_preferences, true)
        PreferenceManager.setDefaultValues(this, R.xml.unit_preferences, true)
    }


    private fun setDI() {
        appComponent = DaggerAppComponent.builder()
            .contextModule(ContextModule(this))
            .build()
    }
}