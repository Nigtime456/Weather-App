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
    BaseFragment<SplashView, WrongSplashPresenter, NavigationController>(R.layout.fragment_splash),
    SplashView {

    override fun getListenerClass(): Class<NavigationController>? = NavigationController::class.java

    override fun getPresenterProvider(): BasePresenterProvider<WrongSplashPresenter> {
        return ViewModelProvider(this).get(SplashPresenterProvider::class.java)
    }

    override fun finishSplash() {
        //удаляем фон
        activity?.window?.setBackgroundDrawable(null)
    }

    override fun navigateToLocationPagesScreen() {
        parentListener?.navigateTo(Screen.Factory.locationPages())
    }

    override fun navigateToSavedLocationsScreen() {
        parentListener?.navigateTo(Screen.Factory.savedLocations())
    }
}
