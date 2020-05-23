/*
 * Сreated by Igor Pokrovsky. 2020/5/23
 */

package com.nigtime.weatherapplication.screen.dailypages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nigtime.weatherapplication.domain.location.SavedLocation
import com.nigtime.weatherapplication.screen.common.BasePresenterProvider

class DailyPagesPresenterProvider(private val location: SavedLocation) :
    BasePresenterProvider<DailyPagesPresenter>() {

    override fun createPresenter(): DailyPagesPresenter {
        return DailyPagesPresenter(location, appContainer.forecastProvider)
    }

    class Factory constructor(private val location: SavedLocation) :
        ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DailyPagesPresenterProvider::class.java)) {
                return DailyPagesPresenterProvider(location) as T
            } else {
                error("unknown view model class")
            }
        }
    }


}