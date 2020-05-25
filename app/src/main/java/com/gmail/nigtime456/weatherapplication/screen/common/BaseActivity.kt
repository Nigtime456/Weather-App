/*
 * Сreated by Igor Pokrovsky. 2020/5/22
 */

package com.gmail.nigtime456.weatherapplication.screen.common

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.gmail.nigtime456.weatherapplication.common.App
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import leakcanary.AppWatcher
import java.util.*


/**
 * Базовая активити, управляет сменой локали и добавляет
 * утилитные методы.
 */
abstract class BaseActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()
    private val settingsProvider = App.INSTANCE.appContainer.settingsProvider
    protected val presenterFactory = App.INSTANCE.presenterFactory

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

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        observeLangChanges()
        observeThemeChanges()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
        AppWatcher.objectWatcher.watch(this, "activity leak")
    }

    private fun observeLangChanges() {
        settingsProvider
            .observeLangChanges()
            .subscribe { recreate() }
            .disposeOnStop()
    }

    private fun observeThemeChanges() {
        settingsProvider
            .observeThemeChanges()
            .subscribe { recreate() }
            .disposeOnStop()
    }

    protected fun Disposable.disposeOnStop() {
        compositeDisposable.add(this)
    }

}