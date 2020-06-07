/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

package com.gmail.nigtime456.weatherapplication.screen.splash

import com.gmail.nigtime456.weatherapplication.screen.base.BasePresenter

interface SplashContract {

    interface View {
        //ui
        fun showCurrentForecastScreen()
        fun showLocationsScreen()
    }

    interface Presenter : BasePresenter<View> {
        //actions
        fun dispatchScreen()
    }
}