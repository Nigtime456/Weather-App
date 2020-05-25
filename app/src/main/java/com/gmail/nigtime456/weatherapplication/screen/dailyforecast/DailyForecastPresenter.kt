/*
 * Сreated by Igor Pokrovsky. 2020/5/23
 */

package com.gmail.nigtime456.weatherapplication.screen.dailyforecast

import com.gmail.nigtime456.weatherapplication.domain.forecast.ForecastProvider
import com.gmail.nigtime456.weatherapplication.domain.settings.SettingsProvider
import com.gmail.nigtime456.weatherapplication.screen.common.BasePresenter

class DailyForecastPresenter constructor(
    private val forecastProvider: ForecastProvider,
    private val settingsProvider: SettingsProvider
) : BasePresenter<DailyForecastView>(TAG) {
    companion object {
        const val TAG = "daily_forecast"
    }


}