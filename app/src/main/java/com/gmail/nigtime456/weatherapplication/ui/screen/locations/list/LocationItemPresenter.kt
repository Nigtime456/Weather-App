/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.locations.list

import com.gmail.nigtime456.weatherapplication.domain.forecast.CurrentForecast
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.domain.repository.ForecastProvider
import com.gmail.nigtime456.weatherapplication.domain.repository.SettingsProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject

class LocationItemPresenter @Inject constructor(
    private val forecastProvider: ForecastProvider,
    private val settingsProvider: SettingsProvider,
    private val savedLocation: SavedLocation
) : LocationItemContract.Presenter {
    private val compositeDisposable = CompositeDisposable()
    private var view: LocationItemContract.View? = null

    override fun loadForecast(view: LocationItemContract.View) {
        this.view = view
        compositeDisposable += forecastProvider.getCurrentForecast(savedLocation.createRequestParams())
            .subscribe(this::showCurrentForecast) {/* ignore errors */ }
    }

    override fun stop() {
        compositeDisposable.clear()
        view = null
    }

    private fun showCurrentForecast(currentForecast: CurrentForecast) {
        view?.showCurrentTemp(currentForecast.temp, settingsProvider.getUnitOfTemp())
        view?.showCurrentTempIco(currentForecast.ico)
    }
}