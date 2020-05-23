/*
 * Сreated by Igor Pokrovsky. 2020/5/23
 */

package com.nigtime.weatherapplication.screen.dailyforecast

import com.nigtime.weatherapplication.domain.forecast.ForecastProvider
import com.nigtime.weatherapplication.domain.settings.SettingsProvider
import com.nigtime.weatherapplication.screen.common.BasePresenter

class DailyForecastPresenter constructor(
    private val forecastProvider: ForecastProvider,
    private val settingsProvider: SettingsProvider
) : BasePresenter<DailyForecastView>(TAG) {
    companion object {
        const val TAG = "daily_forecast"
    }


}