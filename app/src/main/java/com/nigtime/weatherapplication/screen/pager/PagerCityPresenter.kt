/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screen.pager

import com.nigtime.weatherapplication.common.rx.SchedulerProvider
import com.nigtime.weatherapplication.domain.city.ForecastCitiesRepository
import com.nigtime.weatherapplication.screen.common.BasePresenter
import io.reactivex.Observable

class PagerCityPresenter(
    schedulerProvider: SchedulerProvider,
    private val forecastCitiesRepository: ForecastCitiesRepository
) : BasePresenter<PagerCityView>(
    schedulerProvider
) {
    private var currentPage = 0
    private var expectInsert = false

    override fun onShowView() {
        super.onShowView()
        provideCities()
    }

    fun setPagerPosition(page: Int) {
        currentPage = page
    }

    fun provideCities() {
        forecastCitiesRepository.getListCities()
            .subscribeOn(schedulerProvider.syncDatabase())
            .observeOn(schedulerProvider.ui())
            .subscribeAndHandleError() { list ->
                require(list.isNotEmpty()) { "pager screen must not receive empty cities list!" }

                getView()?.submitListToPager(list)
                getView()?.submitListToNavView(list)
                getView()?.setCurrentPage(currentPage)
                getView()?.setCurrentNavItem(currentPage)
            }
    }


    fun onClickChangeCityList() {
        getView()?.navigateToWishListScreen()
    }

    fun onClickNavigationItem(index: Int) {
        getView()?.setCurrentPage(index)
    }

    fun onPageScrolled(position: Int) {
        currentPage = position
        getView()?.setCurrentNavItem(currentPage)
    }

    fun observeItemClicks(observeItemClicks: Observable<Int>) {
        observeItemClicks.subscribe { selectedPosition ->
            currentPage = selectedPosition
        }.disposeOnDetach()
    }

    fun observeInsertNewCity(observeInsert: Observable<Int>) {
        observeInsert.subscribe { selectedPosition ->
            if (expectInsert){
                currentPage = selectedPosition
            }
        }.disposeOnDetach()
    }

    fun onClickAddCity() {
        expectInsert = true
        getView()?.navigateToSearchCityScreen()
    }
}