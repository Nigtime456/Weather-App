/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screen.pager

import com.nigtime.weatherapplication.common.rx.SchedulerProvider
import com.nigtime.weatherapplication.domain.city.CityForForecast
import com.nigtime.weatherapplication.domain.city.ForecastCitiesRepository
import com.nigtime.weatherapplication.screen.common.BasePresenter

class PagerCityPresenter(
    schedulerProvider: SchedulerProvider,
    private val forecastCitiesRepository: ForecastCitiesRepository
) : BasePresenter<PagerCityView>(
    schedulerProvider
) {
    private var currentPage = 0

    fun setPagerPosition(page: Int) {
        currentPage = page
    }

    fun provideCities() {
        forecastCitiesRepository.getListCities()
            .subscribeOn(schedulerProvider.syncDatabase())
            .map { Pair(it, makeNavigationList(it)) }
            .observeOn(schedulerProvider.ui())
            .subscribeAndHandleError() { pair ->
                require(pair.first.isNotEmpty()) { "pager screen must not receive empty cities list!" }

                getView()?.submitPageList(pair.first)
                getView()?.submitNavigationList(pair.second)
                getView()?.setCurrentPage(currentPage, false)
            }
    }

    private fun makeNavigationList(item: List<CityForForecast>): List<Pair<Int, String>> {
        return item.mapIndexed { index, city -> Pair(index + 1000, city.cityName) }
    }

    fun onClickChangeCityList() {
        getView()?.navigateToWishListScreen()
    }

    fun onClickNavigationItem(index: Int) {
        getView()?.setCurrentPage(index - 1000, true)
    }

    fun onPageScrolled(position: Int) {
        getView()?.selectNavigationItem(position + 1000)
    }
}