/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.currentforecast

import com.nigtime.weatherapplication.screen.common.BaseViewModel
import com.nigtime.weatherapplication.screen.common.PresenterProvider

class CurrentForecastViewModel : BaseViewModel(), PresenterProvider<CurrentForecastPresenter> {

    val presenter: CurrentForecastPresenter = CurrentForecastPresenter(
        appContainer.schedulerProvider,
        appContainer.forecastManager
    )

    override fun providePresenter(): CurrentForecastPresenter {
        return presenter
    }
}