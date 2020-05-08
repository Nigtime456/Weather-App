/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.pager

import com.nigtime.weatherapplication.screen.common.BasePresenterFactory

class PagerCityPresenterFactory : BasePresenterFactory<PagerCityPresenter>() {
    private val presenter: PagerCityPresenter = PagerCityPresenter(
        appContainer.schedulerProvider,
        appContainer.forecastCitiesRepository
    )

    override fun createPresenter(): PagerCityPresenter = presenter
}