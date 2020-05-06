/*
 * Сreated by Igor Pokrovsky. 2020/5/6
 */

package com.nigtime.weatherapplication.common.di

import android.content.Context
import com.nigtime.weatherapplication.screens.currentforecast.CurrentForecastPresenter

class CurrentForecastContainer constructor(context: Context, appContainer: AppContainer) {
    val currentForecastPresenter: CurrentForecastPresenter

    init {
        currentForecastPresenter = CurrentForecastPresenter(
            appContainer.schedulerProvider,
            appContainer.forecastManager,
            appContainer.settingsManager
        )
    }
}