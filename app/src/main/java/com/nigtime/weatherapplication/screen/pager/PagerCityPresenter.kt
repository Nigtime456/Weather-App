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

    private companion object {
        const val LIST_CITIES = "list cities"
    }

    private var currentPage = 0

    override fun onAttach() {
        super.onAttach()

        if (retainedContainer.contains(LIST_CITIES)) {
            submitList(retainedContainer.getAs(LIST_CITIES))
        } else {
            provideCities()
        }
    }

    private fun provideCities() {
        forecastCitiesRepository.getListCities()
            .subscribeOn(schedulerProvider.syncDatabase())
            .observeOn(schedulerProvider.ui())
            .subscribeAndHandleError(onNext = this::submitList)
    }

    private fun submitList(list: List<CityForForecast>) {
        retainedContainer.put(LIST_CITIES, list)

        getView()?.submitListToPager(list)
        getView()?.submitListToNavView(list)
        getView()?.setCurrentPage(currentPage)
        getView()?.setCurrentNavItem(currentPage)
    }


    fun onChangeCityListClick() {
        getView()?.navigateToWishListScreen()
    }

    fun onNavigationItemClick(index: Int) {
        getView()?.setCurrentPage(index)
    }

    fun onPageScrolled(position: Int) {
        currentPage = position
        getView()?.setCurrentNavItem(currentPage)
    }

    fun onAddCityClick() {
        getView()?.navigateToSearchCityScreen()
    }

    fun onCityInserted(position: Int) {
        currentPage = position
    }

    fun onCitySelected(position: Int) {
        currentPage = position
    }

    fun onSettingsClick() {
        getView()?.navigateToSettingsScreen()
    }
}