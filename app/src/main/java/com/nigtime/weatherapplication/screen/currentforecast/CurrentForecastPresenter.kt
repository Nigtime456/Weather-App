/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screen.currentforecast

import android.util.Log
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.rx.SchedulerProvider
import com.nigtime.weatherapplication.domain.forecast.CurrentForecast
import com.nigtime.weatherapplication.domain.forecast.DailyForecast
import com.nigtime.weatherapplication.domain.forecast.ForecastManager
import com.nigtime.weatherapplication.domain.forecast.HourlyForecast
import com.nigtime.weatherapplication.domain.location.SavedLocation
import com.nigtime.weatherapplication.domain.params.RequestParams
import com.nigtime.weatherapplication.domain.settings.*
import com.nigtime.weatherapplication.screen.common.BasePresenter
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.Subject


class CurrentForecastPresenter(
    schedulerProvider: SchedulerProvider,
    private val currentLocation: SavedLocation,
    private val forecastManager: ForecastManager,
    private val settingsManager: SettingsManager,
    private val displayedDaysSwitchSubject: Subject<Int>,
    private val verticalScrollSubject: Subject<Int>
) : BasePresenter<CurrentForecastView>(schedulerProvider, TAG) {

    private companion object {
        const val TAG = "current_forecast"

        const val SHOW_5_DAYS = R.id.currentForecastDisplay5days
        const val SHOW_10_DAYS = R.id.currentForecastDisplay10days
        const val SHOW_16_DAYS = R.id.currentForecastDisplay16days
    }

    private val detachDisposable = CompositeDisposable()
    private var currentDisplayedDays = SHOW_5_DAYS
    private lateinit var currentForecast: CurrentForecast
    private lateinit var hourlyForecast: HourlyForecast
    private lateinit var dailyForecast: DailyForecast

    override fun onAttach() {
        super.onAttach()
        Log.d("sas", "onAttach")
        observeDisplayedDaysChanges()
        observeScrollChanges()
        observeUnitSettingsChanges()
        provideForecast()
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("sas", "onDetach")
        detachDisposable.clear()
    }

    private fun observeScrollChanges() {
        detachDisposable += verticalScrollSubject.subscribe { scrollY ->
            getView()?.setVerticalScroll(scrollY)
        }
    }

    private fun observeDisplayedDaysChanges() {
        detachDisposable += displayedDaysSwitchSubject.subscribe { newCount ->
            currentDisplayedDays = newCount
            if (isDataLoaded()) {
                //TODO надо как то распределить по методам этот код
                getView()?.setDailyForecast(
                    getDailyWeatherList(),
                    settingsManager.getUnitOfTemp(),
                    true
                )
                getView()?.selectDaysSwitchButton(currentDisplayedDays)
            }
        }
    }

    private fun observeUnitSettingsChanges() {
        detachDisposable += settingsManager.observeUnitChanges()
            .subscribe(this::onUnitSettingsChanged)
    }

    private fun onUnitSettingsChanged(unit: Any) {
        if (!isDataLoaded()) {
            //данные не загружены - нечего обновлять
            return
        }

        when (unit) {
            is UnitOfTemp -> {
                getView()?.setCurrentTemp(currentForecast.temp, unit)
                getView()?.setCurrentFeelsLikeTemp(currentForecast.feelsLikeTemp, unit)
                getView()?.setHourlyForecast(hourlyForecast.hourlyWeather, unit, false)
                getView()?.setDailyForecast(getDailyWeatherList(), unit, false)
            }
            is UnitOfSpeed -> {
                getView()?.setWind(currentForecast.wind, unit)
            }
            is UnitOfLength -> {
                getView()?.setVisibility(currentForecast.visibility, unit)
            }
            is UnitOfPressure -> {
                getView()?.setPressure(currentForecast.pressure, unit)
            }
            else -> error("unknown unit class = ${unit.javaClass.name}")
        }
    }

    private fun provideForecast() {
        getView()?.setLocationName(currentLocation.getName())
        getView()?.showLoadLayout()

        //во время загрузки - нет возможности обновления
        getView()?.disableRefreshLayout()

        if (isDataLoaded()) {
            logger.d("restore data")
            bindForecastData()
        } else {
            logger.d("load data")
            retainedContainer.clear()
            loadWeather()
        }
    }

    private fun loadWeather() {
        getForecastTriple(currentLocation.createRequestParams(), false)
            .observeOn(schedulerProvider.ui())
            .subscribe(this::onForecastResult) {
                getView()?.showErrorLayout()
                logger.e(it, "error on load data")
            }
            .disposeOnDestroy()
    }

    fun onRequestRefresh() {
        getForecastTriple(currentLocation.createRequestParams(), true)
            .observeOn(schedulerProvider.ui())
            .doFinally { getView()?.stopRefreshing() }
            .subscribe(this::onForecastResult) {
                getView()?.showErrorMessage()
                logger.e(it, "error on load data")
            }
            .disposeOnDestroy()
    }

    private fun getForecastTriple(
        cityParams: RequestParams,
        forceNet: Boolean
    ): Observable<Triple<CurrentForecast, HourlyForecast, DailyForecast>> {
        val current = forecastManager.getCurrentForecast(cityParams, forceNet)
            .subscribeOn(schedulerProvider.io())
        val hourly = forecastManager.getHourlyForecast(cityParams, forceNet)
            .subscribeOn(schedulerProvider.io())
        val daily = forecastManager.getDailyForecast(cityParams, forceNet)
            .subscribeOn(schedulerProvider.io())

        return Observables.zip(current, hourly, daily, ::Triple)
    }

    private fun onForecastResult(triple: Triple<CurrentForecast, HourlyForecast, DailyForecast>) {
        currentForecast = triple.first
        hourlyForecast = triple.second
        dailyForecast = triple.third

        bindForecastData()
    }

    private fun isDataLoaded(): Boolean {
        return ::currentForecast.isInitialized && ::hourlyForecast.isInitialized && ::dailyForecast.isInitialized
    }

    private fun bindForecastData() {

        val unitOfTemp = settingsManager.getUnitOfTemp()
        val unitOfSpeed = settingsManager.getUnitOfSpeed()
        val unitOfPressure = settingsManager.getUnitOfPressure()
        val unitOfLength = settingsManager.getUnitOfLength()

        getView()?.showMainLayout()
        getView()?.setCurrentTemp(currentForecast.temp, unitOfTemp)
        getView()?.setCurrentFeelsLikeTemp(currentForecast.feelsLikeTemp, unitOfTemp)
        getView()?.setCurrentIco(currentForecast.ico)
        getView()?.setCurrentWeatherDescription(currentForecast.description)

        getView()?.setDailyForecast(getDailyWeatherList(), unitOfTemp, true)
        getView()?.selectDaysSwitchButton(currentDisplayedDays)

        getView()?.setHourlyForecast(hourlyForecast.hourlyWeather, unitOfTemp, true)

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

        //включим возможность обновления
        getView()?.enableRefreshLayout()
    }

    private fun getDailyWeatherList(): List<DailyForecast.DailyWeather> {
        return when (currentDisplayedDays) {
            SHOW_5_DAYS -> dailyForecast.dailyWeather.take(5)
            SHOW_10_DAYS -> dailyForecast.dailyWeather.take(10)
            SHOW_16_DAYS -> dailyForecast.dailyWeather.take(16)
            else -> error("invalid switcher ID? = $currentDisplayedDays")
        }
    }


    fun onDisplayedDaysSwitched(checkedId: Int) {
        displayedDaysSwitchSubject.onNext(checkedId)
    }

    fun onScrollChanged(scrollY: Int) {
        verticalScrollSubject.onNext(scrollY)
    }


}