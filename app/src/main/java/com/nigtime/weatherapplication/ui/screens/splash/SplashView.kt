/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.splash

import com.nigtime.weatherapplication.ui.screens.common.MvpView

interface SplashView : MvpView {
    fun playAnimation()
    fun delayedLoadPagerScreen()
    fun delayedLoadSearchCityScreen()
}