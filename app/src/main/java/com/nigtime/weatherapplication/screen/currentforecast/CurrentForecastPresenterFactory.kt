/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.currentforecast

import android.util.Log
import com.nigtime.weatherapplication.screen.common.BasePresenterFactory

class CurrentForecastPresenterFactory : BasePresenterFactory<CurrentForecastPresenter>() {


    val presenter: CurrentForecastPresenter = CurrentForecastPresenter(
        appContainer.schedulerProvider,
        appContainer.forecastManager
    )

    override fun createPresenter(): CurrentForecastPresenter {
        return presenter
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("sas", "cleared ${hashCode()}")
    }
}