/*
 * Сreated by Igor Pokrovsky. 2020/5/17
 */

package com.gmail.nigtime456.weatherapplication.screen.savedlocations.list

import com.gmail.nigtime456.weatherapplication.domain.forecast.CurrentForecast
import com.gmail.nigtime456.weatherapplication.domain.forecast.ForecastProvider
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.domain.settings.SettingsProvider
import com.gmail.nigtime456.weatherapplication.screen.common.BasePresenter

class SavedLocationItemPresenter(
    private val forecastProvider: ForecastProvider,
    private val settingsProvider: SettingsProvider,
    private val savedLocation: SavedLocation
) : BasePresenter<SavedLocationItemView>(TAG) {

    companion object {
        private const val TAG = "item_saved_location"
    }

    override fun onAttach() {
        super.onAttach()
        provideForecast()
    }

    private fun provideForecast() {
        forecastProvider.getCurrentForecast(savedLocation.createRequestParams())
            .subscribe(this::showCurrentForecast) {/* ignore errors */ }
            .disposeOnDestroy()
    }

    private fun showCurrentForecast(currentForecast: CurrentForecast) {
        getView()?.showCurrentTemp(currentForecast.temp, settingsProvider.getUnitOfTemp())
        getView()?.showCurrentTempIco(currentForecast.ico)
    }

}