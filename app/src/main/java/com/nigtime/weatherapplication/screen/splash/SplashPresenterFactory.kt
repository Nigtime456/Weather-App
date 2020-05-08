/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.splash

import com.nigtime.weatherapplication.screen.common.BasePresenterFactory

class SplashPresenterFactory : BasePresenterFactory<WrongSplashPresenter>() {
    private val presenter: WrongSplashPresenter =
        WrongSplashPresenter(appContainer.schedulerProvider, appContainer.referenceCityDao)

    override fun createPresenter(): WrongSplashPresenter = presenter

}