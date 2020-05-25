/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

package com.gmail.nigtime456.weatherapplication.common.di

import com.gmail.nigtime456.weatherapplication.screen.splash.SplashPresenter
import com.gmail.nigtime456.weatherapplication.screen.splash_t.SplashContract

class PresenterFactory(private val appContainer: AppContainer) {

    fun splashPresenter(): SplashContract.Presenter {
        return SplashPresenter(appContainer.savedLocationsRepository)
    }
}