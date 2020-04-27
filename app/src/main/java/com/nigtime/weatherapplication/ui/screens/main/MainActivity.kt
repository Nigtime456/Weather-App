/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.main

import android.annotation.SuppressLint
import android.os.Bundle
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.db.service.AppDatabase
import com.nigtime.weatherapplication.ui.screens.common.BaseActivity
import com.nigtime.weatherapplication.ui.screens.common.ExtendLifecycle
import com.nigtime.weatherapplication.ui.screens.common.NavigationController
import com.nigtime.weatherapplication.ui.screens.common.Screen
import com.nigtime.weatherapplication.ui.screens.listcities.ListCitiesFragment
import com.nigtime.weatherapplication.ui.screens.pager.CityPagerFragment
import com.nigtime.weatherapplication.ui.screens.searchcity.SearchCityFragment
import com.nigtime.weatherapplication.ui.screens.splash.SplashFragment
import com.nigtime.weatherapplication.utility.rx.MainSchedulerProvider

/**
 * Главная активити, управляет только фрагментами, не имеет собственной разметки.
 */
class MainActivity : BaseActivity<MainView, MainPresenter>(), MainView, NavigationController,
    SplashFragment.Listener, SearchCityFragment.Listener, ListCitiesFragment.Listener, CityPagerFragment.ActivityListener{

    override fun provideMvpPresenter(): MainPresenter {
        return MainPresenter(MainSchedulerProvider.INSTANCE)
    }

    override fun provideMvpView(): MainView = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attach(this, lifecycleBus, ExtendLifecycle.DESTROY)
        navigateTo(Screen.Factory.splash())
        test()
    }

    @SuppressLint("CheckResult")
    private fun test() {

        val selectedCityDao = AppDatabase.Instance.get(this).selectedCityDao()
        val geoCityDao = AppDatabase.Instance.get(this).geoCityDao()

    }

    override fun navigateTo(screen: Screen) {
        screen.load(supportFragmentManager, R.id.activityMainFragContainer)
    }

    override fun toBack() {
        onBackPressed()
    }
}
