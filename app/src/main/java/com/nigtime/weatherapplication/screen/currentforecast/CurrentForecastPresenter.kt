/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screen.currentforecast

import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.rx.SchedulerProvider
import com.nigtime.weatherapplication.domain.city.CityForForecast
import com.nigtime.weatherapplication.domain.forecast.CurrentForecast
import com.nigtime.weatherapplication.domain.forecast.DailyForecast
import com.nigtime.weatherapplication.domain.forecast.ForecastManager
import com.nigtime.weatherapplication.domain.forecast.HourlyForecast
import com.nigtime.weatherapplication.domain.param.RequestParams
import com.nigtime.weatherapplication.screen.common.BasePresenter
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.Subject


class CurrentForecastPresenter(
    schedulerProvider: SchedulerProvider,
    private val forecastManager: ForecastManager,
    private val displayedDaysSwitchSubject: Subject<Int>,
    private val verticalScrollSubject: Subject<Int>
) :
    BasePresenter<CurrentForecastView>(
        schedulerProvider, TAG
    ) {

    private companion object {
        const val TAG = "current_forecast"

        const val SHOW_5_DAYS = R.id.currentForecastDisplay5days
        const val SHOW_10_DAYS = R.id.currentForecastDisplay10days
        const val SHOW_16_DAYS = R.id.currentForecastDisplay16days
    }


    private lateinit var currentCity: CityForForecast
    private var dailyWeatherList = emptyList<DailyForecast.DailyWeather>()

    private var currentDisplayedDays = SHOW_5_DAYS

    private var isDataLoaded = false

    //TODO дебаг
    private var startTime = 0L

    override fun onAttach() {
        super.onAttach()
        observeDisplayedDaysChanges()
        observeScrollChanges()
    }

    private fun observeScrollChanges() {
        verticalScrollSubject.subscribe { scrollY ->
            getView()?.setVerticalScroll(scrollY)
        }.disposeOnDetach()
    }

    private fun observeDisplayedDaysChanges() {
        displayedDaysSwitchSubject.subscribe { newCount ->
            currentDisplayedDays = newCount
            showDailyWeather()
        }.disposeOnDetach()
    }

    override fun onDetach() {
        super.onDetach()
        dailyWeatherList = emptyList()
        isDataLoaded = false
    }


    fun provideForecast(cityForForecast: CityForForecast) {
        getView()?.setCityName(cityForForecast.cityName)
        getView()?.showLoadLayout()
        loadWeather(cityForForecast)
    }

    private fun loadWeather(cityForForecast: CityForForecast) {
        startTime = System.currentTimeMillis()
        currentCity = cityForForecast
        val params = RequestParams.CityParams(cityForForecast.cityId)

        getForecastAsSingle(params)
            .observeOn(schedulerProvider.ui())
            .subscribe(this::handleResult, this::onStreamError)
            .disposeOnDetach()
    }

    private fun handleResult(triple: Triple<CurrentForecast, HourlyForecast, DailyForecast>) {
        logger.d("handleResult = ${currentCity.cityName}")
        logger.d("load time = ${System.currentTimeMillis() - startTime}")
        isDataLoaded = true
        val currentForecast = triple.first
        val hourlyForecast = triple.second
        val dailyForecast = triple.third
        dailyWeatherList = triple.third.dailyWeather

        getView()?.showMainLayout()
        getView()?.setCurrentTemp(currentForecast.temp)
        getView()?.setCurrentFeelsLikeTemp(currentForecast.feelsLikeTemp)
        getView()?.setCurrentIco(currentForecast.ico)
        getView()?.setCurrentWeatherDescription(currentForecast.description)
        showDailyWeather()
        getView()?.setHourlyForecast(hourlyForecast.hourlyWeather)
        getView()?.setWind(currentForecast.wind)
        getView()?.setHumidity(currentForecast.humidity)
        getView()?.setPressure(currentForecast.pressure)
        getView()?.setPrecipitation(hourlyForecast.probabilityOfPrecipitation)
        getView()?.setVisibility(currentForecast.visibility)
        getView()?.setAirQuality(currentForecast.airQuality)
        getView()?.setUvIndex(currentForecast.uvIndex)
        getView()?.setClouds(currentForecast.cloudsCoverage)
        getView()?.showClockWidget()
        getView()?.setTimezone(currentForecast.timeZone)
        getView()?.setSunInfo(currentForecast.sunInfo)
    }

    private fun showDailyWeather() {
        //данные не загружены ещё.
        if (!isDataLoaded)
            return

        when (currentDisplayedDays) {
            SHOW_5_DAYS -> {
                getView()?.setDailyForecast(dailyWeatherList.take(5))
            }
            SHOW_10_DAYS -> {
                getView()?.setDailyForecast(dailyWeatherList.take(10))
            }
            SHOW_16_DAYS -> {
                getView()?.setDailyForecast(dailyWeatherList.take(16))
            }
            else -> error("invalid switcher ID? = $currentDisplayedDays")
        }
        //выделить кнопку
        getView()?.selectDaysSwitchButton(currentDisplayedDays)
    }


    override fun onStreamError(throwable: Throwable) {
        super.onStreamError(throwable)
        getView()?.showErrorLayout()
        getView()?.showErrorMessage()
    }

    private fun getForecastAsSingle(cityParams: RequestParams.CityParams): Observable<Triple<CurrentForecast, HourlyForecast, DailyForecast>> {
        val current = forecastManager.getCurrentForecast(cityParams)
            .subscribeOn(schedulerProvider.io())
        val hourly = forecastManager.getHourlyForecast(cityParams)
            .subscribeOn(schedulerProvider.io())
        val daily = forecastManager.getDailyForecast(cityParams)
            .subscribeOn(schedulerProvider.io())

        return Observables.zip(current, hourly, daily, ::Triple)
    }

    fun onDisplayedDaysSwitched(checkedId: Int) {
        displayedDaysSwitchSubject.onNext(checkedId)
    }

    fun onScrollChanged(scrollY: Int) {
        verticalScrollSubject.onNext(scrollY)
    }
}