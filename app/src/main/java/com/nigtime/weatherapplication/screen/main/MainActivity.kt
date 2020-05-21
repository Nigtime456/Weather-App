/*
 * Сreated by Igor Pokrovsky. 2020/5/15
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.App
import com.nigtime.weatherapplication.screen.common.NavigationController
import com.nigtime.weatherapplication.screen.common.Screen
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import leakcanary.AppWatcher


/**
 * Главная активити, управляет только фрагментами, не имеет собственной разметки.
 */
class MainActivity : AppCompatActivity(),
    NavigationController {

    private val settingsManager = App.INSTANCE.appContainer.settingsManager
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragments(savedInstanceState)

        test()
    }

    private fun initFragments(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            navigateTo(Screen.Factory.splash())
        }
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


    //TODO remove it
    @SuppressLint("CheckResult")
    private fun test() {
        Single.fromCallable {
            "d"
        }.subscribeOn(Schedulers.io())
            .subscribeOn(Schedulers.io()).subscribe()
    }

    override fun navigateTo(screen: Screen) {
        screen.load(supportFragmentManager, R.id.mainFragContainer)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this, "activity leak")
    }

    override fun toPreviousScreen() {

        onBackPressed()
    }
}
