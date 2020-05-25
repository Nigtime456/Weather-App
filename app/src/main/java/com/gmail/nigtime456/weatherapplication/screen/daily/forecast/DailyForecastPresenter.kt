/*
 * Сreated by Igor Pokrovsky. 2020/5/23
 */

package com.gmail.nigtime456.weatherapplication.screen.daily.forecast

import com.gmail.nigtime456.weatherapplication.domain.repository.ForecastProvider
import com.gmail.nigtime456.weatherapplication.domain.repository.SettingsProvider
import com.gmail.nigtime456.weatherapplication.screen.common.BasePresenter

class DailyForecastPresenter constructor(
    private val forecastProvider: ForecastProvider,
    private val settingsProvider: SettingsProvider
) : BasePresenter<DailyForecastView>(TAG) {
    companion object {
        const val TAG = "daily_forecast"
    }


}