/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screen.currentforecast

import android.util.Log
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.rx.SchedulerProvider
import com.nigtime.weatherapplication.domain.city.CityForForecast
import com.nigtime.weatherapplication.domain.forecast.CurrentForecast
import com.nigtime.weatherapplication.domain.forecast.DailyForecast
import com.nigtime.weatherapplication.domain.forecast.ForecastManager
import com.nigtime.weatherapplication.domain.forecast.HourlyForecast
import com.nigtime.weatherapplication.domain.param.RequestParams
import com.nigtime.weatherapplication.domain.settings.SettingsManager
import com.nigtime.weatherapplication.screen.common.BasePresenter
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.Subject


class CurrentForecastPresenter(
    schedulerProvider: SchedulerProvider,
    private val cityForForecast: CityForForecast,
    private val forecastManager: ForecastManager,
    private val settingsManager: SettingsManager,
    private val displayedDaysSwitchSubject: Subject<Int>,
    private val verticalScrollSubject: Subject<Int>
) : BasePresenter<CurrentForecastView>(schedulerProvider, TAG) {

    private companion object {
        const val TAG = "current_forecast"

        const val FORECAST = "forecast data"
        const val DAILY_WEATHER = "daily weather data"
        const val UNIT_FORMATTER = "unit formatter"

        const val SHOW_5_DAYS = R.id.currentForecastDisplay5days
        const val SHOW_10_DAYS = R.id.currentForecastDisplay10days
        const val SHOW_16_DAYS = R.id.currentForecastDisplay16days
    }

    private val detachDisposable = CompositeDisposable()
    private var currentDisplayedDays = SHOW_5_DAYS


    override fun onAttach() {
        super.onAttach()
        Log.d("sas", "onAttach")
        observeDisplayedDaysChanges()
        observeScrollChanges()
        observeUnitSettingsChanges()
        //setupScreen()
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
            if (retainedContainer.contains(DAILY_WEATHER)) {
                //данные не загружены ещё
                showDailyWeather(retainedContainer.getAs(DAILY_WEATHER))
            }
        }
    }

    private fun observeUnitSettingsChanges() {
        settingsManager.getUnitFormatter().subscribe { unitFormatter ->
            Log.d("sas", "unitFormatter ")
            retainedContainer.put(UNIT_FORMATTER, unitFormatter)
            setupScreen()
        }.disposeOnDestroy()
    }

    private fun setupScreen() {
        getView()?.setCityName(cityForForecast.cityName)
        getView()?.showLoadLayout()

        getView()?.submitUnitFormatter(retainedContainer.getAs(UNIT_FORMATTER))

        if (retainedContainer.contains(FORECAST)) {
            logger.d("restore data")
            handleResult(retainedContainer.getAs(FORECAST))
        } else {
            logger.d("load data")
            retainedContainer.clear()
            loadWeather()
        }
    }

    private fun loadWeather() {
        val params = RequestParams.CityParams(cityForForecast.cityId)

        getForecastAsSingle(params)
            .observeOn(schedulerProvider.ui())
            .subscribe(this::handleResult, this::onStreamError)
            .disposeOnDestroy()
    }

    private fun handleResult(triple: Triple<CurrentForecast, HourlyForecast, DailyForecast>) {

        val currentForecast = triple.first
        val hourlyForecast = triple.second
        val dailyForecast = triple.third

        retainedContainer.put(FORECAST, triple)
        retainedContainer.put(DAILY_WEATHER, dailyForecast.dailyWeather)

        getView()?.showMainLayout()
        getView()?.setCurrentTemp(currentForecast.temp)
        getView()?.setCurrentFeelsLikeTemp(currentForecast.feelsLikeTemp)
        getView()?.setCurrentIco(currentForecast.ico)
        getView()?.setCurrentWeatherDescription(currentForecast.description)
        showDailyWeather(dailyForecast.dailyWeather)
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

    private fun showDailyWeather(dailyWeather: List<DailyForecast.DailyWeather>) {
        when (currentDisplayedDays) {
            SHOW_5_DAYS -> {
                getView()?.setDailyForecast(dailyWeather.take(5))
            }
            SHOW_10_DAYS -> {
                getView()?.setDailyForecast(dailyWeather.take(10))
            }
            SHOW_16_DAYS -> {
                getView()?.setDailyForecast(dailyWeather.take(16))
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