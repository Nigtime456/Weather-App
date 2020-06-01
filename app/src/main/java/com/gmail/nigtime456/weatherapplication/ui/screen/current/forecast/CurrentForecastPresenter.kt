/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.current.forecast

import android.util.Log
import com.gmail.nigtime456.weatherapplication.domain.forecast.CurrentForecast
import com.gmail.nigtime456.weatherapplication.domain.forecast.DailyForecast
import com.gmail.nigtime456.weatherapplication.domain.forecast.HourlyForecast
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.domain.net.RequestParams
import com.gmail.nigtime456.weatherapplication.domain.repository.ForecastProvider
import com.gmail.nigtime456.weatherapplication.domain.repository.SettingsManager
import com.gmail.nigtime456.weatherapplication.domain.settings.DaysCount
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.Subject
import javax.inject.Inject


class CurrentForecastPresenter @Inject constructor(
    private val view: CurrentForecastContract.View,
    private val currentLocation: SavedLocation,
    private val forecastProvider: ForecastProvider,
    private val settingsManager: SettingsManager,
    private val scrollSubject: Subject<Int>
) : CurrentForecastContract.Presenter {

    private val compositeDisposable = CompositeDisposable()
    private var isDataShown = false

    override fun stop() {
        compositeDisposable.clear()
    }

    override fun provideForecast() {
        observeScrollChanges()
        observeDisplayedDaysChanges()
        setupScreen()
    }

    private fun observeDisplayedDaysChanges() {
        compositeDisposable += settingsManager.observeDaysCountChanges()
            .subscribe(this::setupDisplayedDays)
    }

    private fun setupDisplayedDays(displayedDays: DaysCount) {
        view.setDisplayedDays(displayedDays.getCount())
        view.selectDaysSwitchButton(displayedDays.getId())
    }

    private fun observeScrollChanges() {
        compositeDisposable += scrollSubject.subscribe { scrollY ->
            view.setVerticalScroll(scrollY)
        }
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
        Log.d("sas", "bind[$currentLocation], days = [${settingsManager.getDaysCount()}]")
        val unitOfTemp = settingsManager.getUnitOfTemp()
        val unitOfSpeed = settingsManager.getUnitOfSpeed()
        val unitOfPressure = settingsManager.getUnitOfPressure()
        val unitOfLength = settingsManager.getUnitOfLength()

        val currentForecast = data.first
        val hourlyForecast = data.second
        val dailyForecast = data.third

        view.setCurrentTemp(currentForecast.temp, unitOfTemp)
        view.setCurrentFeelsLikeTemp(currentForecast.feelsLikeTemp, unitOfTemp)
        view.setCurrentIco(currentForecast.ico)
        view.setCurrentWeatherDescription(currentForecast.description)

        view.setDailyForecast(dailyForecast.weatherList, unitOfTemp)

        view.setHourlyForecast(hourlyForecast.weatherList, unitOfTemp)

        view.setWind(currentForecast.wind, unitOfSpeed)

        view.setHumidity(currentForecast.humidity)
        view.setPressure(currentForecast.pressure, unitOfPressure)
        // view.setPrecipitation(hourlyForecast.probabilityOfPrecipitation)
        view.setVisibility(currentForecast.visibility, unitOfLength)
        view.setAirQuality(currentForecast.airQuality)
        view.setUvIndex(currentForecast.uvIndex)
        view.setClouds(currentForecast.cloudsCoverage)
        view.showClockWidget()
        view.setTimezone(currentForecast.timeZone)
        view.setSunInfo(currentForecast.sunInfo)

        setupDisplayedDays(settingsManager.getDaysCount())
    }

    override fun changeDisplayedDays(buttonId: Int) {
        // settingsManager.setDaysCount(DaysCount.getById(buttonId))
    }

    override fun clickDailyWeatherItem(dayIndex: Int) {
        view.showDailyForecastScreen(currentLocation, dayIndex)
    }

    override fun changeScroll(scrollY: Int) {
        scrollSubject.onNext(scrollY)
    }
}