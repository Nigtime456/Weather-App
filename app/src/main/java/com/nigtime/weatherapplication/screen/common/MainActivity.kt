/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.common

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.screen.pager.PagerCityFragment
import com.nigtime.weatherapplication.screen.search.SearchCityFragment
import com.nigtime.weatherapplication.screen.splash.SplashFragment
import com.nigtime.weatherapplication.screen.wishlist.WishCitiesFragment
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import leakcanary.AppWatcher

/**
 * Главная активити, управляет только фрагментами, не имеет собственной разметки.
 */
//TODO возможно эти все интерфейсы и не потребуются
class MainActivity : AppCompatActivity(), NavigationController,
    SplashFragment.ParentListener, SearchCityFragment.ParentListener, WishCitiesFragment.ParentListener,
    PagerCityFragment.ParentListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            navigateTo(Screen.Factory.splash())
        }
        test()
    }


    @SuppressLint("CheckResult")
    private fun test() {
        Single.fromCallable {

        }.subscribeOn(Schedulers.io())
            .subscribeOn(Schedulers.io()).subscribe()
    }

    override fun navigateTo(screen: Screen) {
        screen.load(supportFragmentManager, R.id.mainFragContainer)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppWatcher.objectWatcher.watch(this,"activity leak")
    }

    override fun toBack() {

        onBackPressed()
    }
}
