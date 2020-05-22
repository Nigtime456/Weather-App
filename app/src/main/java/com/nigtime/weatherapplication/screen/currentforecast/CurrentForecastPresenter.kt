/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screen.currentforecast

import com.nigtime.weatherapplication.domain.forecast.CurrentForecast
import com.nigtime.weatherapplication.domain.forecast.DailyForecast
import com.nigtime.weatherapplication.domain.forecast.ForecastProvider
import com.nigtime.weatherapplication.domain.forecast.HourlyForecast
import com.nigtime.weatherapplication.domain.location.SavedLocation
import com.nigtime.weatherapplication.domain.params.RequestParams
import com.nigtime.weatherapplication.domain.settings.SettingsProvider
import com.nigtime.weatherapplication.screen.common.BasePresenter
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.Subject


class CurrentForecastPresenter(
    private val currentLocation: SavedLocation,
    private val forecastProvider: ForecastProvider,
    private val settingsProvider: SettingsProvider,
    private val displayedDaysSwitchSubject: Subject<Int>,
    private val verticalScrollSubject: Subject<Int>
) : BasePresenter<CurrentForecastView>(TAG) {

    private companion object {
        const val TAG = "current_forecast"
    }

    private var isDataShown = false
    private var currentDisplayedDays = CurrentForecastView.DISPLAY_5_DAYS

    override fun onAttach() {
        super.onAttach()
        isDataShown = false
        observeScrollChanges()
        observeDisplayedDaysChanges()
        observeUnitSettingsChanges()
        setupScreen()
    }

    private fun observeDisplayedDaysChanges() {
        displayedDaysSwitchSubject
            .subscribe { buttonId ->
                currentDisplayedDays = buttonId
                setDisplayedDays(buttonId)
            }
            .disposeOnDestroy()
    }

    private fun setDisplayedDays(buttonId: Int) {
        getView()?.setDisplayedDays(getDaysCountByButtonId(buttonId))
        getView()?.selectDaysSwitchButton(buttonId)
    }

    private fun getDaysCountByButtonId(buttonId: Int?): Int {
        return when (buttonId) {
            CurrentForecastView.DISPLAY_5_DAYS -> 5
            CurrentForecastView.DISPLAY_10_DAYS -> 10
            CurrentForecastView.DISPLAY_16_DAYS -> 16
            else -> error("unknown id == $buttonId ?")
        }
    }

    private fun observeScrollChanges() {
        verticalScrollSubject.subscribe { scrollY ->
            getView()?.setVerticalScroll(scrollY)
        }.disposeOnDestroy()
    }

    private fun observeUnitSettingsChanges() {
        settingsProvider.observeUnitsChanges()
            .subscribe {
                //при смене единиц измерения - перезагружаем погоду
                provideForecast(false)
            }
            .disposeOnDestroy()
    }

    private fun setupScreen() {
        getView()?.setLocationName(currentLocation.getName())
        provideForecast(false)
    }

    private fun provideForecast(forceNet: Boolean) {
        getForecastAsTriple(currentLocation.createRequestParams(), forceNet)
            .doOnSubscribe { onLoadStart() }
            .doFinally { onLoadEnd() }
            .subscribe(this::onNextData, this::onErrorLoading) {

            }
            .disposeOnDestroy()
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
            getView()?.showLoadLayout()
        }
        getView()?.disableRefreshLayout()
    }

    private fun onLoadEnd() {
        getView()?.stopRefreshing()
        getView()?.enableRefreshLayout()
    }

    private fun onErrorLoading(error: Throwable) {
        logger.e(error, "error on load forecast")
        if (isDataShown) {
            getView()?.showErrorMessage()
        } else {
            getView()?.showErrorLayout()
        }
    }

    private fun onNextData(data: Triple<CurrentForecast, HourlyForecast, DailyForecast>) {
        isDataShown = true
        getView()?.showMainLayout()
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

        getView()?.setCurrentTemp(currentForecast.temp, unitOfTemp)
        getView()?.setCurrentFeelsLikeTemp(currentForecast.feelsLikeTemp, unitOfTemp)
        getView()?.setCurrentIco(currentForecast.ico)
        getView()?.setCurrentWeatherDescription(currentForecast.description)

        getView()?.setDailyForecast(dailyForecast.dailyWeather, unitOfTemp)
        getView()?.setHourlyForecast(hourlyForecast.hourlyWeather, unitOfTemp)

        getView()?.setWind(currentForecast.wind, unitOfSpeed)

        getView()?.setHumidity(currentForecast.humidity)
        getView()?.setPressure(currentForecast.pressure, unitOfPressure)
        getView()?.setPrecipitation(hourlyForecast.probabilityOfPrecipitation)
        getView()?.setVisibility(currentForecast.visibility, unitOfLength)
        getView()?.setAirQuality(currentForecast.airQuality)
        getView()?.setUvIndex(currentForecast.uvIndex)
        getView()?.setClouds(currentForecast.cloudsCoverage)
        getView()?.showClockWidget()
        getView()?.setTimezone(currentForecast.timeZone)
        getView()?.setSunInfo(currentForecast.sunInfo)

        setDisplayedDays(currentDisplayedDays)
    }


    fun onDisplayedDaysSwitched(checkedId: Int) {
        displayedDaysSwitchSubject.onNext(checkedId)
    }

    fun onScrollChanged(scrollY: Int) {
        verticalScrollSubject.onNext(scrollY)
    }

    fun onRequestRefresh() {
        provideForecast(true)
    }
}