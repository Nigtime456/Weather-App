/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.splash

import com.nigtime.weatherapplication.screen.common.MvpView

interface SplashView : MvpView {
    fun finishSplash()
    fun navigateToPagerScreen()
    fun navigateToWishListScreen()
}