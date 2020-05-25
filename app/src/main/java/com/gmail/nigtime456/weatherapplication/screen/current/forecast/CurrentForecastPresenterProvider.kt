/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.gmail.nigtime456.weatherapplication.screen.current.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.screen.common.BasePresenterProvider
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class CurrentForecastPresenterProvider(private val location: SavedLocation) :
    BasePresenterProvider<CurrentForecastPresenter>() {

    companion object {
        private val daysSwitchSubject: Subject<Int> = BehaviorSubject.create()
        private val verticalScrollSubject: Subject<Int> = BehaviorSubject.create()
    }

    override fun createPresenter(): CurrentForecastPresenter {
        return CurrentForecastPresenter(
            location,
            appContainer.forecastProvider,
            appContainer.settingsProvider,
            daysSwitchSubject,
            verticalScrollSubject
        )
    }


    class Factory(private val location: SavedLocation) :
        ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CurrentForecastPresenterProvider::class.java)) {
                return CurrentForecastPresenterProvider(location) as T
            } else {
                error("unknown view model class")
            }
        }
    }
}