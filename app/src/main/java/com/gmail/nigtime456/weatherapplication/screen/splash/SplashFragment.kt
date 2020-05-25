/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.gmail.nigtime456.weatherapplication.screen.splash

import androidx.lifecycle.ViewModelProvider
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.screen.common.BaseFragment
import com.gmail.nigtime456.weatherapplication.screen.common.BasePresenterProvider
import com.gmail.nigtime456.weatherapplication.screen.common.NavigationController
import com.gmail.nigtime456.weatherapplication.screen.common.Screen

/**
 * TODO поправь сплэш
 * Сплэш должен ждать пока загрузится Pager
 */
class SplashFragment :
    BaseFragment<SplashView, SplashPresenter, SplashFragment.SplashParent>(R.layout.fragment_splash),
    SplashView {

    interface SplashParent : NavigationController {
        fun removeSplashBackground()
    }


    override fun getListenerClass(): Class<SplashParent> = SplashParent::class.java

    override fun getPresenterProvider(): BasePresenterProvider<SplashPresenter> {
        return ViewModelProvider(this).get(SplashPresenterProvider::class.java)
    }

    override fun finishSplash() {
        attachedListener?.removeSplashBackground()
    }

    override fun navigateToLocationPagesScreen() {
        attachedListener?.navigateTo(Screen.Factory.locationPages())
    }

    override fun navigateToSavedLocationsScreen() {
        attachedListener?.navigateTo(Screen.Factory.savedLocations())
    }

}
