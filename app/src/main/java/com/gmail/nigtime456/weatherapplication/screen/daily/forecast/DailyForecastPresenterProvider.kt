/*
 * Сreated by Igor Pokrovsky. 2020/5/23
 */

package com.gmail.nigtime456.weatherapplication.screen.daily.forecast

import com.gmail.nigtime456.weatherapplication.screen.common.BasePresenterProvider

class DailyForecastPresenterProvider : BasePresenterProvider<DailyForecastPresenter>() {
    override fun createPresenter(): DailyForecastPresenter {
        return DailyForecastPresenter(appContainer.forecastProvider, appContainer.settingsProvider)
    }
}