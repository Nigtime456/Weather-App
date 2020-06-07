/*
 * Сreated by Igor Pokrovsky. 2020/6/3
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.gmail.nigtime456.weatherapplication.screen.locations.list

import com.gmail.nigtime456.weatherapplication.data.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.data.repository.ForecastProvider
import com.gmail.nigtime456.weatherapplication.data.repository.SettingsProvider
import javax.inject.Inject

class LocationViewHolderPresenterFactory @Inject constructor(
    private val forecastProvider: ForecastProvider,
    private val settingsProvider: SettingsProvider
) {

    fun getItemPresenter(location: SavedLocation): LocationViewHolderPresenter {
        return LocationViewHolderPresenter(
            forecastProvider,
            settingsProvider,
            location
        )
    }
}