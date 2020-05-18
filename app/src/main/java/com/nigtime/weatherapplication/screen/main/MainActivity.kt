/*
 * Сreated by Igor Pokrovsky. 2020/5/15
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.screen.common.NavigationController
import com.nigtime.weatherapplication.screen.common.Screen
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import leakcanary.AppWatcher
import kotlin.system.measureTimeMillis


/**
 * Главная активити, управляет только фрагментами, не имеет собственной разметки.
 */
class MainActivity : AppCompatActivity(),
    NavigationController {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        R.styleable.ListPreference
        if (savedInstanceState == null) {
            navigateTo(Screen.Factory.splash())
        }
        test()
    }

    //TODO remove it
    @SuppressLint("CheckResult")
    private fun test() {
        val m = measureTimeMillis {
            LayoutInflater.from(this).inflate(R.layout.fragment_current_forecast_main, null)
        }

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
