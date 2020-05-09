/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.currentforecast

import android.util.Log
import com.nigtime.weatherapplication.screen.common.BasePresenterFactory
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class CurrentForecastPresenterFactory : BasePresenterFactory<CurrentForecastPresenter>() {

    companion object {
        private val daysSwitchSubject: Subject<Int> = BehaviorSubject.create()
        private val verticalScrollSubject: Subject<Int> = BehaviorSubject.create()
    }

    val presenter: CurrentForecastPresenter = CurrentForecastPresenter(
        appContainer.schedulerProvider,
        appContainer.forecastManager,
        daysSwitchSubject,
        verticalScrollSubject
    )

    override fun createPresenter(): CurrentForecastPresenter {
        return presenter
    }

}