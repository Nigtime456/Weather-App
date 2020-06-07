/*
 * Сreated by Igor Pokrovsky. 2020/6/3
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.gmail.nigtime456.weatherapplication.screen.locations.list

import com.gmail.nigtime456.weatherapplication.data.forecast.CurrentForecast
import com.gmail.nigtime456.weatherapplication.data.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.data.repository.ForecastProvider
import com.gmail.nigtime456.weatherapplication.data.repository.SettingsProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject

class LocationViewHolderPresenter @Inject constructor(
    private val forecastProvider: ForecastProvider,
    private val settingsProvider: SettingsProvider,
    private val location: SavedLocation
) : LocationViewHolderContract.Presenter {

    private val compositeDisposable = CompositeDisposable()
    private var view: LocationViewHolderContract.View? = null

    override fun loadForecast(view: LocationViewHolderContract.View) {
        this.view = view
        compositeDisposable += forecastProvider.getCurrentForecast(location.createRequestParams())
            .subscribe(this::showCurrentForecast) { /* ignore errors */ }
    }

    override fun stop() {
        compositeDisposable.clear()
        view = null
    }

    private fun showCurrentForecast(currentForecast: CurrentForecast) {
        view?.showCurrentTemp(currentForecast.weather.temp, settingsProvider.getUnitOfTemp())
        view?.showCurrentTempIco(currentForecast.weather.ico)
    }
}