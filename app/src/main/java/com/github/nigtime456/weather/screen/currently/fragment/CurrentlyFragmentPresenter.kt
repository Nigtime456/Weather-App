/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */


package com.github.nigtime456.weather.screen.currently.fragment

import com.github.nigtime456.weather.data.forecast.Forecast
import com.github.nigtime456.weather.data.forecast.PartOfDay
import com.github.nigtime456.weather.data.location.SavedLocation
import com.github.nigtime456.weather.data.repository.ForecastProvider
import com.github.nigtime456.weather.data.repository.SettingsProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber
import javax.inject.Inject

class CurrentlyFragmentPresenter @Inject constructor(
    private val view: CurrentlyFragmentContract.View,
    private val location: SavedLocation,
    private val forecastProvider: ForecastProvider,
    private val settingsProvider: SettingsProvider
) : CurrentlyFragmentContract.Presenter {

    private companion object {
        const val TAG = "currently"
    }

    private val compositeDisposable = CompositeDisposable()
    private lateinit var partOfDay: PartOfDay

    override fun stop() {
        compositeDisposable.clear()
    }

    override fun provideForecast() {
        compositeDisposable += forecastProvider.getForecast(location, false)
            .doOnSubscribe {
                view.showLoadLayout()
                view.disableRefreshLayout()
            }
            .doFinally {
                view.enableRefreshLayout()
            }
            .subscribe(this::onNext) {
                view.showErrorLayout()
                logError(it)
            }
    }

    override fun clickDailyForecast(position: Int) {
        view.showDailyForecastScreen(location, partOfDay, position)
    }

    private fun logError(throwable: Throwable) {
        Timber.tag(TAG).e(throwable, "error")
    }

    override fun onViewForeground() {
        view.setLocationName(location)
    }

    override fun refreshData() {
        compositeDisposable += forecastProvider.getForecast(location, true)
            .doOnEach { view.stopRefreshing(!it.isOnError) }
            .subscribe(this::onNext) {
                view.showErrorMessage()
                logError(it)
            }
    }

    private fun onNext(forecast: Forecast) {
        view.showMainLayout()

        view.setUpdateTime(forecast.timestampMs)
        view.setTimeZone(forecast.timeZone)

        partOfDay = forecast.partOfDay
        view.setPartOfDay(partOfDay)

        view.showCurrentlyWeather(
            forecast.currently.temp,
            forecast.currently.apparentTemp,
            forecast.currently.weatherCode,
            settingsProvider.getUnitOfTemp()
        )
        view.showWind(
            forecast.currently.windDegrees,
            forecast.currently.windSpeed,
            forecast.currently.windGust,
            settingsProvider.getUnitOfSpeed()
        )
        view.showHumidity(forecast.currently.humidity)
        view.showUvIndex(forecast.currently.uvIndex)
        view.showPressure(forecast.currently.pressure, settingsProvider.getUnitOfPressure())
        view.showVisibility(forecast.currently.visibility, settingsProvider.getUnitOfLength())
        view.showDewPoint(forecast.currently.dewPoint, settingsProvider.getUnitOfTemp())
        view.showCloudsCoverage(forecast.currently.cloudCoverage)

        view.showHourlyForecast(
            forecast.hourly,
            settingsProvider.getUnitOfTemp(),
            forecast.timeZone
        )
        view.showDailyForecast(forecast.daily, settingsProvider.getUnitOfTemp())
    }

}