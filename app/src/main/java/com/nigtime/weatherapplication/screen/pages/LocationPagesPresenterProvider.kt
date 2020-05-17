/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.pages

import com.nigtime.weatherapplication.common.di.AppContainer
import com.nigtime.weatherapplication.screen.common.BasePresenterProvider

class LocationPagesPresenterProvider : BasePresenterProvider<LocationPagesPresenter>() {

    override fun createPresenter(appContainer: AppContainer): LocationPagesPresenter {
        return LocationPagesPresenter(
            appContainer.schedulerProvider,
            appContainer.forecastLocationsRepository
        )
    }
}