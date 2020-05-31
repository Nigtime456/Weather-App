/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.splash

import com.gmail.nigtime456.weatherapplication.ui.screen.base.BasePresenter

interface SplashContract {

    interface View {
        fun showCurrentForecastScreen()
        fun showLocationsScreen()
    }

    interface Presenter : BasePresenter<View> {
        fun dispatchScreen()
    }
}