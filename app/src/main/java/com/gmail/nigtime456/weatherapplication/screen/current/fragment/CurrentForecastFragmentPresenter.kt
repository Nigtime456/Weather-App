/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.gmail.nigtime456.weatherapplication.screen.current.fragment

import android.util.Log
import com.gmail.nigtime456.weatherapplication.data.forecast.CurrentForecast
import com.gmail.nigtime456.weatherapplication.data.forecast.DailyForecast
import com.gmail.nigtime456.weatherapplication.data.forecast.HourlyForecast
import com.gmail.nigtime456.weatherapplication.data.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.data.net.RequestParams
import com.gmail.nigtime456.weatherapplication.data.repository.ForecastProvider
import com.gmail.nigtime456.weatherapplication.data.repository.SettingsProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject

class CurrentForecastFragmentPresenter @Inject constructor(
    private val view: CurrentForecastFragmentContract.View,
    private val currentLocation: SavedLocation,
    private val forecastProvider: ForecastProvider,
    private val settingsProvider: SettingsProvider
) : CurrentForecastFragmentContract.Presenter {

    private val compositeDisposable = CompositeDisposable()
    private var isDataShown = false

    override fun stop() {
        compositeDisposable.clear()
    }

    override fun provideForecast() {
        setupScreen()
    }

    private fun setupScreen() {
        view.setLocationName(currentLocation.getName())
        provideForecast(false)
    }

    override fun refreshData() {
        provideForecast(true)
    }

    private fun provideForecast(forceNet: Boolean) {
        val start = System.currentTimeMillis()
        compositeDisposable += getForecastAsTriple(currentLocation.createRequestParams(), forceNet)
            .doOnSubscribe { onLoadStart() }
            .doFinally {
                Log.d("sas", "end = ${System.currentTimeMillis() - start}")
                onLoadEnd()
            }
            .subscribe(this::onNextDataLoaded, this::onErrorLoading)
    }

    private fun getForecastAsTriple(
        requestParams: RequestParams,
        forceNet: Boolean
    ): Observable<Triple<CurrentForecast, HourlyForecast, DailyForecast>> {

        val current = forecastProvider.getCurrentForecast(requestParams, forceNet)
        val hourly = forecastProvider.getHourlyForecast(requestParams, forceNet)
        val daily = forecastProvider.getDailyForecast(requestParams, forceNet)

        return Observables.zip(current, hourly, daily, ::Triple)
    }

    private fun onLoadStart() {
        if (!isDataShown) {
            view.showLoadLayout()
        }
        view.disableRefreshLayout()
    }

    private fun onLoadEnd() {
        view.stopRefreshing()
        view.enableRefreshLayout()
    }

    private fun onErrorLoading(error: Throwable) {
        error.printStackTrace()
        if (isDataShown) {
            view.showErrorMessage()
        } else {
            view.showErrorLayout()
        }
    }

    private fun onNextDataLoaded(data: Triple<CurrentForecast, HourlyForecast, DailyForecast>) {
        isDataShown = true
        view.showMainLayout()
        bindData(data)
    }

    private fun bindData(data: Triple<CurrentForecast, HourlyForecast, DailyForecast>) {

        val currentForecast = data.first
        val hourlyForecast = data.second
        val dailyForecast = data.third

        view.setTimezone(currentForecast.timeZone)

        view.showCurrentWeather(0, currentForecast.weather, settingsProvider.getUnitOfTemp())
        view.showHourlyWeather(1, hourlyForecast.weatherList, settingsProvider.getUnitOfTemp())
        view.showDailyWeather(2, dailyForecast.weatherList, settingsProvider.getUnitOfTemp())
        view.showDetailedWeather(
            3,
            currentForecast.detailedWeather,
            settingsProvider.getUnitOfSpeed(),
            settingsProvider.getUnitOfPressure(),
            settingsProvider.getUnitOfLength()
        )
        view.showEnvironment(4, currentForecast.environment)
    }
}