/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.pager

import com.nigtime.weatherapplication.common.di.AppContainer
import com.nigtime.weatherapplication.screen.common.BasePresenterProvider

class PagerCityPresenterProvider : BasePresenterProvider<PagerCityPresenter>() {

    override fun createPresenter(appContainer: AppContainer): PagerCityPresenter {
        return PagerCityPresenter(
            appContainer.schedulerProvider,
            appContainer.forecastCitiesRepository
        )
    }
}