/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.splash


import com.nigtime.weatherapplication.ui.screens.common.BasePresenter
import com.nigtime.weatherapplication.utility.rx.SchedulerProvider

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