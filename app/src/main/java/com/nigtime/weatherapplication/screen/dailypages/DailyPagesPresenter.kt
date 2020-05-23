/*
 * Сreated by Igor Pokrovsky. 2020/5/23
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/23
 */

package com.nigtime.weatherapplication.screen.dailypages

import android.util.Log
import com.nigtime.weatherapplication.common.util.DateFormatFactory
import com.nigtime.weatherapplication.domain.forecast.DailyForecast
import com.nigtime.weatherapplication.domain.forecast.ForecastProvider
import com.nigtime.weatherapplication.domain.location.SavedLocation
import com.nigtime.weatherapplication.screen.common.BasePresenter
import io.reactivex.rxkotlin.subscribeBy

class DailyPagesPresenter constructor(
    private val currentLocation: SavedLocation,
    private val forecastProvider: ForecastProvider
) : BasePresenter<DailyPagesView>(TAG) {

    companion object {
        const val TAG = "daily_pages"
    }

    private val weekDayFormatter = DateFormatFactory.getShortWeekdayWithDayFormatter()
    private var currentPage: Int = 0

    override fun onAttach() {
        super.onAttach()
        init()
    }

    private fun init() {
        getView()?.setLocation(currentLocation.getName())
        provideForecast(false)
    }

    fun setCurrentPage(page: Int) {
        currentPage = page
    }

    fun onRequestRefresh() {
        provideForecast(true)
    }

    private fun provideForecast(forceNet: Boolean) {
        startMeasure()
        forecastProvider.getDailyForecast(currentLocation.createRequestParams(), forceNet)
            .map(this::mapDailyWeatherToDateList)
            .doFinally { getView()?.stopRefreshing() }
            .subscribeBy(onNext = this::onResult, onError = this::onError)
            .disposeOnDestroy()
    }

    private fun mapDailyWeatherToDateList(dailyForecast: DailyForecast): List<String> {
        return dailyForecast.dailyWeather.map { item ->
            weekDayFormatter.format(item.unixTimestamp)
        }
    }

    private fun onResult(titles: List<String>) {
        Log.d("sas", "DAILY FORECAST LOADED = [${endMeasure()}]")
        getView()?.setViewPager(currentLocation)
        getView()?.setTabLayout(titles)
        getView()?.setPage(currentPage)
    }

    private fun onError(throwable: Throwable) {
        logger.e(throwable)
        getView()?.showErrorMessage()
    }

    fun onNavigationClick() {
        getView()?.navigateToPreviousScreen()
    }


}