/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.savedlocations

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
            appContainer.savedLocationsRepository,
            messageDispatcher
        )
    }

    override fun onCleared() {
        super.onCleared()
        itemsPresentersMap.forEach { entry -> entry.value.destroy() }
    }

    override fun getItemPresenter(savedLocation: SavedLocation): SavedLocationItemPresenter {
        return itemsPresentersMap.getOrPut(savedLocation.getKey()) {
            createItemPresenter(savedLocation)
        }
    }

    private fun createItemPresenter(savedLocation: SavedLocation): SavedLocationItemPresenter {
        return SavedLocationItemPresenter(
            appContainer.forecastManager,
            appContainer.settingsManager,
            savedLocation
        )
    }
}