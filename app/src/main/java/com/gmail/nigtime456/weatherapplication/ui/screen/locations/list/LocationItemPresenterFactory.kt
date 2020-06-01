/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.locations.list

import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.domain.repository.ForecastProvider
import com.gmail.nigtime456.weatherapplication.domain.repository.SettingsManager
import javax.inject.Inject

class LocationItemPresenterFactory @Inject constructor(
    private val forecastProvider: ForecastProvider,
    private val settingsManager: SettingsManager
) {
    private val presenters = mutableMapOf<Long, LocationItemPresenter>()

    fun getItemPresenter(savedLocation: SavedLocation): LocationItemPresenter {
        return presenters.getOrPut(savedLocation.getKey()) {
            createItemPresenter(savedLocation)
        }
    }

    private fun createItemPresenter(savedLocation: SavedLocation): LocationItemPresenter {
        return LocationItemPresenter(
            forecastProvider,
            settingsManager,
            savedLocation
        )
    }
}