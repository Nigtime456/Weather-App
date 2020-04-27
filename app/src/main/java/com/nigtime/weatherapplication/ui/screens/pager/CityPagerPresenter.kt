/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.ui.screens.pager

import com.nigtime.weatherapplication.db.repository.SelectedCitySource
import com.nigtime.weatherapplication.ui.screens.common.BasePresenter
import com.nigtime.weatherapplication.utility.rx.SchedulerProvider

class CityPagerPresenter(
    schedulerProvider: SchedulerProvider,
    private val selectedCitySource: SelectedCitySource
) : BasePresenter<CityPagerView>(
    schedulerProvider
) {

    fun provideCities() {
        selectedCitySource.getListCityForForecast()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribeAndHandleError(false) { list ->
                require(list.isNotEmpty()) { "pager screen must not get empty cities list!" }
                getView()?.submitList(list)
            }
    }
}