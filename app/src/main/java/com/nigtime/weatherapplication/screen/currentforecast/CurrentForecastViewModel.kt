/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.currentforecast

import androidx.lifecycle.ViewModel
import com.nigtime.weatherapplication.common.App
import com.nigtime.weatherapplication.screen.common.PresenterProvider

class CurrentForecastViewModel : ViewModel(), PresenterProvider<CurrentForecastPresenter> {

        val presenter : CurrentForecastPresenter
    init {

        val appContainer = App.INSTANCE.appContainer
        presenter =  CurrentForecastPresenter(
            appContainer.schedulerProvider,
            appContainer.forecastManager,
            appContainer.settingsManager
        )
    }

    override fun providePresenter(): CurrentForecastPresenter {
        val appContainer = App.INSTANCE.appContainer
        return presenter
    }
}