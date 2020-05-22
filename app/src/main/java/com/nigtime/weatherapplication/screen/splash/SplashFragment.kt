/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.splash

import androidx.lifecycle.ViewModelProvider
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.screen.common.BaseFragment
import com.nigtime.weatherapplication.screen.common.BasePresenterProvider
import com.nigtime.weatherapplication.screen.common.NavigationController
import com.nigtime.weatherapplication.screen.common.Screen

/**
 * TODO поправь сплэш
 * Сплэш должен ждать пока загрузится Pager
 */
class SplashFragment :
    BaseFragment<SplashView, WrongSplashPresenter, SplashFragment.SplashParent>(R.layout.fragment_splash),
    SplashView {

    interface SplashParent : NavigationController {
        fun removeSplashBackground()
    }


    override fun getListenerClass(): Class<SplashParent> = SplashParent::class.java

    override fun getPresenterProvider(): BasePresenterProvider<WrongSplashPresenter> {
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
