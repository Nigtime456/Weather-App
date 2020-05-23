/*
 * Сreated by Igor Pokrovsky. 2020/5/15
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.main

import android.os.Bundle
import android.view.WindowManager
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.screen.common.BaseActivity
import com.nigtime.weatherapplication.screen.common.NavigationController
import com.nigtime.weatherapplication.screen.common.Screen
import com.nigtime.weatherapplication.screen.splash.SplashFragment


/**
 * Главная активити, управляет только фрагментами, не имеет собственной разметки.
 */
class MainActivity : BaseActivity(), NavigationController, SplashFragment.SplashParent {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(settingsProvider.getTheme())
        setContentView(R.layout.activity_main)
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            navigateTo(Screen.Factory.splash())
        } else {
            removeSplashBackground()
        }
    }

    override fun removeSplashBackground() {
        window.setBackgroundDrawable(null)
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun navigateTo(screen: Screen) {
        screen.load(supportFragmentManager, R.id.mainFragContainer)
    }

    override fun toPreviousScreen() {
        onBackPressed()
    }
}
