/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.splash


import com.nigtime.weatherapplication.screen.common.BasePresenter
import com.nigtime.weatherapplication.common.rx.SchedulerProvider

class SplashPresenter(schedulerProvider: SchedulerProvider) :
    BasePresenter<SplashView>(schedulerProvider, TAG) {

    companion object {
        private const val TAG = "splash"
    }

    fun dispatchScreen() {
        getView()?.playSplashAnimation()

    }

    private fun dispatchResult(hasSelectedCities: Boolean) {
        if (hasSelectedCities) {
            getView()?.navigateToPagerScreen()
        } else {
            getView()?.navigateToWishListScreen()
        }
    }
}