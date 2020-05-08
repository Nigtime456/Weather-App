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
    private val displayedDaysSwitchSubject: Subject<Int>
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

    private var dailyWeatherList = emptyList<DailyForecast.DailyWeather>()
    private var currentDisplayedDays = SHOW_5_DAYS

    //TODO дебаг
    private var startTime = 0L

    override fun onAttach() {
        super.onAttach()
        displayedDaysSwitchSubject.subscribe { newCount ->
            currentDisplayedDays = newCount
            showDailyForecast()
        }
            .disposeOnDetach()

    }

    override fun onDetach() {
        super.onDetach()
        dailyWeatherList = emptyList()
    }


    fun provideForecast(cityForForecast: CityForForecast) {
        getView()?.setCityName(cityForForecast.cityName)
        getView()?.showLoadLayout()
        loadWeather(cityForForecast)
    }

    private fun loadWeather(cityForForecast: CityForForecast) {
        startTime = System.currentTimeMillis()

        val params = RequestParams.CityParams(cityForForecast.cityId)

        getForecastAsSingle(params)
            .observeOn(schedulerProvider.ui())
            .subscribe(this::handleResult, this::onStreamError)
            .disposeOnDetach()
    }

    private fun handleResult(triple: Triple<CurrentForecast, HourlyForecast, DailyForecast>) {
        logger.d("load time = ${System.currentTimeMillis() - startTime}")

        dailyWeatherList = triple.third.dailyWeather

        getView()?.showMainLayout()
        getView()?.setCurrentForecast(triple.first)
        getView()?.setHourlyForecast(triple.second.hourlyWeather)
        showDailyForecast()
    }

    private fun showDailyForecast() {
        //данные не загружены ещё.
        if (dailyWeatherList.isEmpty())
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
        getView()?.selectDaysSwitcherButton(currentDisplayedDays)
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

}