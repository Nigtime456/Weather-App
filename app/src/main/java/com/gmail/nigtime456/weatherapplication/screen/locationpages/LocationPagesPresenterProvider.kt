/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.gmail.nigtime456.weatherapplication.screen.locationpages

import com.gmail.nigtime456.weatherapplication.screen.common.BasePresenterProvider

class LocationPagesPresenterProvider : BasePresenterProvider<LocationPagesPresenter>() {

    override fun createPresenter(): LocationPagesPresenter {
        return LocationPagesPresenter(appContainer.savedLocationsRepository)
    }
}