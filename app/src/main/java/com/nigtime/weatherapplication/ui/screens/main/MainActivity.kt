/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.ui.screens.common.BaseActivity
import com.nigtime.weatherapplication.ui.screens.common.NavigationController
import com.nigtime.weatherapplication.ui.screens.common.Screen
import com.nigtime.weatherapplication.ui.screens.wishlist.WishCitiesFragment
import com.nigtime.weatherapplication.ui.screens.pager.PagerCityFragment
import com.nigtime.weatherapplication.ui.screens.search.SearchCityFragment
import com.nigtime.weatherapplication.ui.screens.splash.SplashFragment

/**
 * Главная активити, управляет только фрагментами, не имеет собственной разметки.
 */
class MainActivity : BaseActivity(), MainView, NavigationController,
    SplashFragment.Listener, SearchCityFragment.Listener, WishCitiesFragment.Listener, PagerCityFragment.ActivityListener{



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigateTo(Screen.Factory.splash())
        test()
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("sas","a result")
        data?.let {
            val int = it.extras!!.getInt(SearchCityFragment.EXTRA_INSERTED_POSITION)
            Log.d("sas","a pos = $int")
        }
    }

    @SuppressLint("CheckResult")
    private fun test() {

    }

    override fun navigateTo(screen: Screen) {
        screen.load(supportFragmentManager, R.id.mainFragContainer)
    }

    override fun toBack() {
        onBackPressed()
    }
}
