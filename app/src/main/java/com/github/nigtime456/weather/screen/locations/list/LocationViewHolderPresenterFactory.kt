/*
 * Сreated by Igor Pokrovsky. 2020/6/3
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.github.nigtime456.weather.screen.locations.list

import com.github.nigtime456.weather.data.location.SavedLocation
import com.github.nigtime456.weather.data.repository.ForecastProvider
import com.github.nigtime456.weather.data.repository.SettingsProvider
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