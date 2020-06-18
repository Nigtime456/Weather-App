/*
 * Сreated by Igor Pokrovsky. 2020/6/13
 */

package com.github.nigtime456.weather.screen.daily.activity

import com.github.nigtime456.weather.data.forecast.DailyForecast
import com.github.nigtime456.weather.data.forecast.Forecast
import com.github.nigtime456.weather.data.forecast.PartOfDay
import com.github.nigtime456.weather.data.location.SavedLocation
import com.github.nigtime456.weather.data.repository.ForecastProvider
import com.github.nigtime456.weather.utils.ui.DateFormatters
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber
import javax.inject.Inject

class DailyActivityPresenter @Inject constructor(
    private val view: DailyActivityContract.View,
    private val location: SavedLocation,
    private val partOfDay: PartOfDay,
    private var dayIndex: Int,
    private val forecastProvider: ForecastProvider
) : DailyActivityContract.Presenter {

    private companion object {
        const val TAG = "daily"
    }

    private val compositeDisposable = CompositeDisposable()
    private val dateFormatter = DateFormatters.getShortWeekdayWithDayFormatter()

    override fun stop() {
        compositeDisposable.clear()
    }

    override fun start() {
        view.showLocationName(location)
        resolveScreenBackground()
    }

    private fun resolveScreenBackground() {
        if (partOfDay == PartOfDay.DAY) {
            view.showLightBackground()
        } else {
            view.showDarkBackground()
        }
    }

    override fun provideForecast() {
        compositeDisposable += forecastProvider.getForecast(location)
            .subscribe(this::onNext)
    }

    private fun onNext(forecast: Forecast) {
        view.setUpdateTime(forecast.timestampMs)
        view.showForecast(forecast.daily)
        view.showTabs(mapTitles(forecast.daily))
        view.setPage(dayIndex)
    }

    override fun refreshData() {
        compositeDisposable += forecastProvider.getForecast(location, true)
            .doOnEach { view.stopRefreshing(!it.isOnError) }
            .subscribe(this::onNext) {
                logError(it)
                view.showErrorMessage()
            }
    }

    override fun scrollPage(page: Int) {
        dayIndex = page
    }

    private fun logError(throwable: Throwable) {
        Timber.tag(TAG).e(throwable, "error")
    }

    private fun mapTitles(daily: List<DailyForecast>): List<String> {
        return daily.map { forecast -> dateFormatter.format(forecast.timeMs) }
    }

}