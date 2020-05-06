/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screens.splash

import com.nigtime.weatherapplication.screens.common.MvpView

interface SplashView : MvpView {
    fun playSplashAnimation()
    fun navigateToPagerScreen()
    fun navigateToWishListScreen()
}