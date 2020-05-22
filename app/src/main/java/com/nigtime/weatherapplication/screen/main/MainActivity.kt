/*
 * Сreated by Igor Pokrovsky. 2020/5/15
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.main

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.App
import com.nigtime.weatherapplication.screen.common.NavigationController
import com.nigtime.weatherapplication.screen.common.Screen
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import leakcanary.AppWatcher


/**
 * Главная активити, управляет только фрагментами, не имеет собственной разметки.
 */
class MainActivity : AppCompatActivity(),
    NavigationController {

    private val settingsManager = App.INSTANCE.appContainer.settingsProvider
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("sas", "lang = ${getString(R.string.lang_tag)}")
        setContentView(R.layout.activity_main)
        initFragments(savedInstanceState)
    }

    private fun initFragments(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            navigateTo(Screen.Factory.splash())
        }
    }

    override fun attachBaseContext(newBase: Context) {
        return super.attachBaseContext(createLanguageContext(newBase))
    }

    private fun createLanguageContext(context: Context): Context {
        val lang = settingsManager.getLangCode()
        return LocaleHelper.onAttach(context, lang)
    }

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        if (overrideConfiguration != null) {
            val uiMode = overrideConfiguration.uiMode
            overrideConfiguration.setTo(baseContext.resources.configuration)
            overrideConfiguration.uiMode = uiMode
        }
        super.applyOverrideConfiguration(overrideConfiguration)
    }

    override fun onStart() {
        super.onStart()
        observeLangChanges()
        observeThemeChanges()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this, "activity leak")
    }


    private fun observeLangChanges() {
        compositeDisposable += settingsManager
            .observeLangChanges()
            .subscribe {
                recreate()
            }
    }

    private fun observeThemeChanges() {
        compositeDisposable += settingsManager
            .observeThemeChanges()
            .subscribe {
                recreate()
            }
    }

    override fun navigateTo(screen: Screen) {
        screen.load(supportFragmentManager, R.id.mainFragContainer)
    }

    override fun toPreviousScreen() {

        onBackPressed()
    }
}
