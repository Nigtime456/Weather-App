/*
 * Сreated by Igor Pokrovsky. 2020/5/29
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.base

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gmail.nigtime456.weatherapplication.App
import com.gmail.nigtime456.weatherapplication.di.AppComponent
import com.gmail.nigtime456.weatherapplication.tools.rx.AutoDisposable
import leakcanary.AppWatcher
import java.util.*


/**
 * Базовая активити, управляет сменой локали и темы.
 */
abstract class BaseActivity : AppCompatActivity() {
    private val autoDisposable = AutoDisposable(lifecycle)
    private val settingsProvider by lazy { App.getComponent().getSettingsProvider() }

    override fun attachBaseContext(newBase: Context) {
        return super.attachBaseContext(createLanguageContext(newBase))
    }

    private fun createLanguageContext(context: Context): Context {
        val locale = settingsProvider.getLocale()
        Locale.setDefault(locale)
        val newConfiguration = context.resources.configuration
        newConfiguration.setLocale(locale)
        newConfiguration.setLayoutDirection(locale)
        return context.createConfigurationContext(newConfiguration)
    }

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        if (overrideConfiguration != null) {
            val uiMode = overrideConfiguration.uiMode
            overrideConfiguration.setTo(baseContext.resources.configuration)
            overrideConfiguration.uiMode = uiMode
        }
        super.applyOverrideConfiguration(overrideConfiguration)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initDi(App.getComponent())
        super.onCreate(savedInstanceState)
        observeLangChanges()
        observeThemeChanges()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this, "activity leak")
    }

    private fun observeLangChanges() {
        autoDisposable.add(
            settingsProvider
                .observeLangChanges()
                .subscribe { recreate() })
    }

    private fun observeThemeChanges() {
        autoDisposable.add(
            settingsProvider
                .observeThemeChanges()
                .subscribe { recreate() })
    }

    protected abstract fun initDi(appComponent: AppComponent)
}