/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.savedlocations

import android.util.Log
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.rx.RxDelayedMessageDispatcher
import com.nigtime.weatherapplication.domain.location.SavedLocation
import com.nigtime.weatherapplication.screen.common.BasePresenterProvider
import com.nigtime.weatherapplication.screen.savedlocations.list.ItemPresenterFactory
import com.nigtime.weatherapplication.screen.savedlocations.list.SavedLocationItemPresenter

class SavedLocationPresenterProvider : BasePresenterProvider<SavedLocationsPresenter>(),
    ItemPresenterFactory {
    private val itemsPresentersMap = mutableMapOf<Long, SavedLocationItemPresenter>()


    override fun createPresenter(): SavedLocationsPresenter {
        val removeDelay =
            appContainer.appContext.resources.getInteger(R.integer.current_locations_remove_delay)
        val messageDispatcher =
            RxDelayedMessageDispatcher(removeDelay.toLong(), appContainer.schedulerProvider)

        return SavedLocationsPresenter(
            appContainer.schedulerProvider,
            appContainer.savedLocationsRepository,
            messageDispatcher
        )
    }

    override fun getItemPresenter(savedLocation: SavedLocation): SavedLocationItemPresenter {
        return itemsPresentersMap.getOrPut(savedLocation.getKey()) {
            Log.d("sas", "create = ${savedLocation.getName()}")
            createItemPresenter(savedLocation)
        }
    }

    private fun createItemPresenter(savedLocation: SavedLocation): SavedLocationItemPresenter {
        return SavedLocationItemPresenter(
            appContainer.schedulerProvider,
            appContainer.forecastManager,
            appContainer.settingsManager,
            savedLocation
        )
    }
}