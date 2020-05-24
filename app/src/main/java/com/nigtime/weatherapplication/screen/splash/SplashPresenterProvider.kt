/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.splash

import com.nigtime.weatherapplication.screen.common.BasePresenterProvider

class SplashPresenterProvider : BasePresenterProvider<SplashPresenter>() {

    override fun createPresenter(): SplashPresenter {
        return SplashPresenter(appContainer.savedLocationsRepository)
    }

}