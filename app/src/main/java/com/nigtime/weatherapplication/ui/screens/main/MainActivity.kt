/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.domain.params.RequestParams
import com.nigtime.weatherapplication.domain.settings.Lang
import com.nigtime.weatherapplication.ui.screens.common.BaseActivity
import com.nigtime.weatherapplication.ui.screens.common.NavigationController
import com.nigtime.weatherapplication.ui.screens.common.Screen
import com.nigtime.weatherapplication.ui.screens.pager.PagerCityFragment
import com.nigtime.weatherapplication.ui.screens.search.SearchCityFragment
import com.nigtime.weatherapplication.ui.screens.splash.SplashFragment
import com.nigtime.weatherapplication.ui.screens.wishlist.WishCitiesFragment
import com.nigtime.weatherapplication.utility.di.ForecastManagerFactory
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
        ForecastManagerFactory.getForecastManager()
            .getCurrentForecast(RequestParams.CityParams(Lang.EN, 524901))
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d("sas","result $it")
            },{
                Log.d("sas","error $it")
            })
    }

    override fun navigateTo(screen: Screen) {
        screen.load(supportFragmentManager, R.id.mainFragContainer)
    }

    override fun toBack() {
        onBackPressed()
    }
}
