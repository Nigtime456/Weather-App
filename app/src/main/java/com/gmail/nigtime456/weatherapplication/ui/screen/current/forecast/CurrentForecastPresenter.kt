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

import com.gmail.nigtime456.weatherapplication.domain.forecast.CurrentForecast
import com.gmail.nigtime456.weatherapplication.domain.forecast.DailyForecast
import com.gmail.nigtime456.weatherapplication.domain.forecast.HourlyForecast
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.domain.net.RequestParams
import com.gmail.nigtime456.weatherapplication.domain.repository.ForecastProvider
import com.gmail.nigtime456.weatherapplication.domain.repository.SettingsProvider
import com.gmail.nigtime456.weatherapplication.ui.screen.current.forecast.di.DaysSwitchSubject
import com.gmail.nigtime456.weatherapplication.ui.screen.current.forecast.di.ScrollSubject
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
    private val settingsProvider: SettingsProvider,
    @DaysSwitchSubject private val displayedDaysSwitchSubject: Subject<Int>,
    @ScrollSubject private val verticalScrollSubject: Subject<Int>
) : CurrentForecastContract.Presenter {


    private val compositeDisposable = CompositeDisposable()
    private var isDataShown = false
    private var currentDisplayedDays = CurrentForecastContract.DISPLAY_5_DAYS


    override fun stop() {
        compositeDisposable.clear()
    }

    override fun viewReady() {
        observeScrollChanges()
        observeDisplayedDaysChanges()
        observeUnitSettingsChanges()
        setupScreen()
    }

    private fun observeDisplayedDaysChanges() {
        compositeDisposable += displayedDaysSwitchSubject
            .subscribe { buttonId ->
                currentDisplayedDays = buttonId
                setDisplayedDays(buttonId)
            }

    }

    private fun setDisplayedDays(buttonId: Int) {
        view.setDisplayedDays(getDaysCountByButtonId(buttonId))
        view.selectDaysSwitchButton(buttonId)
    }

    private fun getDaysCountByButtonId(buttonId: Int?): Int {
        return when (buttonId) {
            CurrentForecastContract.DISPLAY_5_DAYS -> 5
            CurrentForecastContract.DISPLAY_10_DAYS -> 10
            CurrentForecastContract.DISPLAY_16_DAYS -> 16
            else -> error("unknown id == $buttonId ?")
        }
    }

    private fun observeScrollChanges() {
        compositeDisposable += verticalScrollSubject.subscribe { scrollY ->
            view.setVerticalScroll(scrollY)
        }
    }

    private fun observeUnitSettingsChanges() {
        compositeDisposable += settingsProvider.observeUnitsChanges()
            .subscribe {
                //при смене единиц измерения - перезагружаем погоду
                provideForecast(false)
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
        compositeDisposable += getForecastAsTriple(currentLocation.createRequestParams(), forceNet)
            .doOnSubscribe { onLoadStart() }
            .doFinally { onLoadEnd() }
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
        val unitOfTemp = settingsProvider.getUnitOfTemp()
        val unitOfSpeed = settingsProvider.getUnitOfSpeed()
        val unitOfPressure = settingsProvider.getUnitOfPressure()
        val unitOfLength = settingsProvider.getUnitOfLength()

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

        setDisplayedDays(currentDisplayedDays)
    }

    override fun changeDisplayedDays(buttonId: Int) {
        displayedDaysSwitchSubject.onNext(buttonId)
    }


    override fun clickDailyWeatherItem(dayIndex: Int) {
        view.showDailyForecastScreen(currentLocation, dayIndex)
    }

    override fun changeScroll(scrollY: Int) {
        verticalScrollSubject.onNext(scrollY)
    }


}