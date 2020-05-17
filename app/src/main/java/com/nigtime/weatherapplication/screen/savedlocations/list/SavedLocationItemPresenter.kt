/*
 * Сreated by Igor Pokrovsky. 2020/5/17
 */

package com.nigtime.weatherapplication.screen.savedlocations.list

import android.util.Log
import com.nigtime.weatherapplication.common.rx.SchedulerProvider
import com.nigtime.weatherapplication.domain.forecast.CurrentForecast
import com.nigtime.weatherapplication.domain.forecast.ForecastManager
import com.nigtime.weatherapplication.domain.location.SavedLocation
import com.nigtime.weatherapplication.domain.settings.SettingsManager
import com.nigtime.weatherapplication.screen.common.BasePresenter

class SavedLocationItemPresenter(
    schedulerProvider: SchedulerProvider,
    private val forecastManager: ForecastManager,
    private val settingsManager: SettingsManager,
    private val savedLocation: SavedLocation
) : BasePresenter<SavedLocationItemView>(schedulerProvider) {

    private lateinit var currentForecast: CurrentForecast

    override fun onAttach() {
        super.onAttach()
        Log.d("sas", "attach ${savedLocation.getName()}")
        if (::currentForecast.isInitialized) {
            showCurrentForecast()
        } else {
            provideForecast()
        }
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("sas", "detach ${savedLocation.getName()}")
    }

    private fun provideForecast() {
        forecastManager.getCurrentForecast(savedLocation.createRequestParams())
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(this::onResult) { /* ignore errors*/ }
            .disposeOnDestroy()
    }

    private fun onResult(currentForecast: CurrentForecast) {
        this.currentForecast = currentForecast
        showCurrentForecast()
    }

    private fun showCurrentForecast() {
        getView()?.showCurrentTemp(currentForecast.temp, settingsManager.getUnitOfTemp())
        getView()?.showCurrentTempIco(currentForecast.ico)
    }

}