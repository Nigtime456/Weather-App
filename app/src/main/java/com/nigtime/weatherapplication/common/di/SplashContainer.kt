/*
 * Сreated by Igor Pokrovsky. 2020/5/6
 */

package com.nigtime.weatherapplication.common.di

import android.content.Context
import com.nigtime.weatherapplication.screens.splash.WrongSplashPresenter

class SplashContainer constructor(context: Context, appContainer: AppContainer) {
    val splashPresenter =
        WrongSplashPresenter(appContainer.schedulerProvider, appContainer.referenceCityDao)
}
