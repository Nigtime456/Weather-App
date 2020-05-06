/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.main

import android.annotation.SuppressLint
import android.os.Bundle
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.App
import com.nigtime.weatherapplication.ui.screens.common.BaseActivity
import com.nigtime.weatherapplication.ui.screens.common.NavigationController
import com.nigtime.weatherapplication.ui.screens.common.Screen
import com.nigtime.weatherapplication.ui.screens.pager.PagerCityFragment
import com.nigtime.weatherapplication.ui.screens.search.SearchCityFragment
import com.nigtime.weatherapplication.ui.screens.splash.SplashFragment
import com.nigtime.weatherapplication.ui.screens.wishlist.WishCitiesFragment
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Главная активити, управляет только фрагментами, не имеет собственной разметки.
 */
class MainActivity : BaseActivity(), MainView, NavigationController,
    SplashFragment.Listener, SearchCityFragment.Listener, WishCitiesFragment.Listener,
    PagerCityFragment.ActivityListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigateTo(Screen.Factory.splash())
        test()
    }


    @SuppressLint("CheckResult")
    private fun test() {
        Single.fromCallable {
            App.INSTANCE.database.wishCityDao().getMaxListIndex()
        }.subscribeOn(Schedulers.io())
            .subscribeOn(Schedulers.io()).subscribe()
    }

    override fun navigateTo(screen: Screen) {
        screen.load(supportFragmentManager, R.id.mainFragContainer)
    }

    override fun toBack() {
        onBackPressed()
    }
}
