/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.ui.screens.currentforecast

import com.nigtime.weatherapplication.common.rx.SchedulerProvider
import com.nigtime.weatherapplication.domain.city.CityForForecast
import com.nigtime.weatherapplication.domain.param.RequestParams
import com.nigtime.weatherapplication.domain.repository.ForecastManager
import com.nigtime.weatherapplication.domain.weather.CurrentForecast
import com.nigtime.weatherapplication.domain.weather.DailyForecast
import com.nigtime.weatherapplication.domain.weather.HourlyForecast
import com.nigtime.weatherapplication.ui.helper.UnitFormatter
import com.nigtime.weatherapplication.ui.screens.common.BasePresenter
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables

class CurrentForecastPresenter(
    schedulerProvider: SchedulerProvider,
    private val forecastManager: ForecastManager,
    private val unitFormatter: UnitFormatter
) :
    BasePresenter<CurrentForecastView>(
        schedulerProvider, TAG
    ) {

    private companion object {
        const val TAG = "current_forecast"
    }

    private var startTime = 0L

    fun setUp(cityForForecast: CityForForecast) {
        getView()?.setCityName(cityForForecast.cityName)
        getView()?.showLoadAnimation()
        loadWeather(cityForForecast)
    }

    private fun loadWeather(cityForForecast: CityForForecast) {
        val cityParams = RequestParams.CityParams(cityForForecast.cityId)
        startTime = System.currentTimeMillis()
        getForecastSingle(cityParams)
            .observeOn(schedulerProvider.ui())
            .subscribe(this::handleResult, this::onStreamError)
            .disposeOnDetach()
    }

    private fun handleResult(triple: Triple<CurrentForecast, HourlyForecast, DailyForecast>) {
        logger.d("load time = ${System.currentTimeMillis() - startTime}")
        val currentForecast = triple.first
        getView()?.showMainLayout()
        getView()?.setCurrentTemp(unitFormatter.formatTemp(currentForecast.weatherInfo.temp))
        getView()?.setCurrentFeelsLikeTemp(unitFormatter.formatFeelsLikeTemp(currentForecast.weatherInfo.feelsLikeTemp))
        getView()?.setCurrentDescription(currentForecast.weatherInfo.description)
        getView()?.setCurrentTempIco(currentForecast.weatherInfo.ico)

        val hourly = triple.second
        getView()?.showHourlyForecast(hourly.hourlyWeatherList)
    }

    override fun onStreamError(throwable: Throwable) {
        getView()?.showErrorView()
        getView()?.showErrorMessage()
    }

    private fun getForecastSingle(cityParams: RequestParams.CityParams): Observable<Triple<CurrentForecast, HourlyForecast, DailyForecast>> {
        val current = forecastManager.getCurrentForecast(cityParams)
            .subscribeOn(schedulerProvider.io())
        val hourly = forecastManager.getHourlyForecast(cityParams)
            .subscribeOn(schedulerProvider.io())
        val daily = forecastManager.getDailyForecast(cityParams)
            .subscribeOn(schedulerProvider.io())

        return Observables.zip(current, hourly, daily, ::Triple)
    }

}