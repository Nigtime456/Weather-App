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
import com.nigtime.weatherapplication.screen.common.NavigationController
import com.nigtime.weatherapplication.screen.common.Screen
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import leakcanary.AppWatcher

/**
 * Главная активити, управляет только фрагментами, не имеет собственной разметки.
 */
class MainActivity : AppCompatActivity(),
    NavigationController {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            navigateTo(Screen.Factory.splash())
        }
        test()
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
