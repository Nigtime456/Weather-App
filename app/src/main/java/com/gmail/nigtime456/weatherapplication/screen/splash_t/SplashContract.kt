/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

package com.gmail.nigtime456.weatherapplication.screen.splash_t

import com.gmail.nigtime456.weatherapplication.common.mvp.BasePresenter
import com.gmail.nigtime456.weatherapplication.common.mvp.BaseView

interface SplashContract {
    interface View : BaseView<Presenter> {
        fun navigateToCurrentForecastScreen()
        fun navigateToSavedLocationsScreen()
    }

    interface Presenter : BasePresenter<View> {
        fun dispatchScreen()
    }
}