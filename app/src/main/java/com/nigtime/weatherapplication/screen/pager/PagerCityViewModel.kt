/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.pager

import com.nigtime.weatherapplication.screen.common.BaseViewModel
import com.nigtime.weatherapplication.screen.common.PresenterProvider

class PagerCityViewModel : BaseViewModel(), PresenterProvider<PagerCityPresenter> {
    private val presenter: PagerCityPresenter = PagerCityPresenter(
        appContainer.schedulerProvider,
        appContainer.forecastCitiesRepository
    )

    override fun providePresenter(): PagerCityPresenter = presenter
}