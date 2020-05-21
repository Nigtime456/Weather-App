/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.pages

import com.nigtime.weatherapplication.screen.common.BasePresenterProvider

class LocationPagesPresenterProvider : BasePresenterProvider<LocationPagesPresenter>() {

    override fun createPresenter(): LocationPagesPresenter {
        return LocationPagesPresenter(appContainer.savedLocationsRepository)
    }
}