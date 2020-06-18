/*
 * Сreated by Igor Pokrovsky. 2020/6/16
 */

package com.github.nigtime456.weather.screen.daily.fragment

import com.github.nigtime456.weather.data.forecast.DailyForecast
import com.github.nigtime456.weather.data.repository.SettingsProvider
import javax.inject.Inject

class DailyFragmentPresenter @Inject constructor(
    private val view: DailyFragmentContract.View,
    private val forecast: DailyForecast,
    private val settingsProvider: SettingsProvider
) : DailyFragmentContract.Presenter {

    override fun start() {
        view.showWeather(
            forecast.weatherCode,
            forecast.tempHigh,
            forecast.tempLow,
            settingsProvider.getUnitOfTemp()
        )

        view.showWind(
            forecast.windDegrees,
            forecast.windSpeed,
            forecast.windGust,
            settingsProvider.getUnitOfSpeed()
        )
        view.showHumidity(forecast.humidity)
        view.showUvIndex(forecast.uvIndex)
        view.showPressure(forecast.pressure, settingsProvider.getUnitOfPressure())
        view.showVisibility(forecast.visibility, settingsProvider.getUnitOfLength())
        view.showDewPoint(forecast.dewPoint, settingsProvider.getUnitOfTemp())
        view.showCloudsCoverage(forecast.cloudCoverage)
        view.showSunriseAndSunset(forecast.sunrise, forecast.sunset, forecast.timeZone)
        view.showMoonPhase(forecast.moonPhase)
    }

    override fun stop() {

    }

}