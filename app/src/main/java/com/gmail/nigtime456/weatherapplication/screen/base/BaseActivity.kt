/*
 * Сreated by Igor Pokrovsky. 2020/5/29
 */

package com.gmail.nigtime456.weatherapplication.screen.base

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gmail.nigtime456.weatherapplication.App
import com.gmail.nigtime456.weatherapplication.di.AppComponent
import com.gmail.nigtime456.weatherapplication.tools.rx.AutoDisposable
import java.util.*

/**
 * Базовая активити, управляет сменой локали и темы.
 */
abstract class BaseActivity : AppCompatActivity() {
    //<editor-fold desc = "fields">
    private val autoDisposable = AutoDisposable(lifecycle)
    private val settingsProvider = App.getComponent().getSettingsProvider()

    //</editor-fold>

    //<editor-fold desc = "set locale">

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

    //</editor-fold>

    //<editor-fold desc = "lifecycle">
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(settingsProvider.getTheme())
        initDi(App.getComponent())
        super.onCreate(savedInstanceState)
        observeLangChanges()
        observeThemeChanges()
    }

    //</editor-fold>

    //<editor-fold desc = "observe events">

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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    //</editor-fold>

    //<editor-fold desc = "util">
    protected open fun initDi(appComponent: AppComponent) {

    }

    //</editor-fold>
}