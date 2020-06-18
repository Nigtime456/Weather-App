/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

package com.github.nigtime456.weather.screen.splash

import com.github.nigtime456.weather.screen.base.BasePresenter

interface SplashContract {

    interface View {
        //ui
        fun showCurrentForecastScreen()
        fun showLocationsScreen()
    }

    interface Presenter : BasePresenter {
        //actions
        fun dispatchScreen()
    }
}