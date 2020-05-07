/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import com.nigtime.weatherapplication.common.App
import com.nigtime.weatherapplication.screen.common.PresenterProvider

class SplashViewModel : ViewModel(), PresenterProvider<WrongSplashPresenter> {
    private val presenter: WrongSplashPresenter

    init {
        val appContainer = App.INSTANCE.appContainer
        presenter =
            WrongSplashPresenter(appContainer.schedulerProvider, appContainer.referenceCityDao)
    }

    override fun providePresenter(): WrongSplashPresenter = presenter


}