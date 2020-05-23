/*
 * Сreated by Igor Pokrovsky. 2020/5/23
 */

package com.nigtime.weatherapplication.screen.dailyforecast

import com.nigtime.weatherapplication.screen.common.BasePresenterProvider

class DailyForecastPresenterProvider : BasePresenterProvider<DailyForecastPresenter>() {
    override fun createPresenter(): DailyForecastPresenter {
        return DailyForecastPresenter(appContainer.forecastProvider, appContainer.settingsProvider)
    }
}