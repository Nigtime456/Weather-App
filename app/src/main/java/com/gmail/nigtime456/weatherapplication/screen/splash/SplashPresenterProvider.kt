/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.gmail.nigtime456.weatherapplication.screen.splash

import com.gmail.nigtime456.weatherapplication.screen.common.BasePresenterProvider

class SplashPresenterProvider : BasePresenterProvider<SplashPresenter>() {

    override fun createPresenter(): SplashPresenter {
        return SplashPresenter(appContainer.savedLocationsRepository)
    }

}