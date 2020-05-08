/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.splash

import com.nigtime.weatherapplication.screen.common.BaseViewModel
import com.nigtime.weatherapplication.screen.common.PresenterProvider

class SplashViewModel : BaseViewModel(), PresenterProvider<WrongSplashPresenter> {
    private val presenter: WrongSplashPresenter =
        WrongSplashPresenter(appContainer.schedulerProvider, appContainer.referenceCityDao)

    override fun providePresenter(): WrongSplashPresenter = presenter


}