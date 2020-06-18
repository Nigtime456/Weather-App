/*
 * Сreated by Igor Pokrovsky. 2020/6/3
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.github.nigtime456.weather.screen.locations.list

import com.github.nigtime456.weather.data.forecast.Forecast
import com.github.nigtime456.weather.data.location.SavedLocation
import com.github.nigtime456.weather.data.repository.ForecastProvider
import com.github.nigtime456.weather.data.repository.SettingsProvider
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
        compositeDisposable += forecastProvider.getForecast(location)
            .subscribe(this::onNext)
    }

    private fun onNext(forecast: Forecast) {
        view?.showCurrentlyWeather(
            forecast.currently.temp,
            forecast.currently.weatherCode,
            settingsProvider.getUnitOfTemp()
        )
    }

    override fun stop() {
        compositeDisposable.clear()
        view = null
    }

}