/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.gmail.nigtime456.weatherapplication.screen.savedlocations

import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.common.rx.RxDelayedMessageDispatcher
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.screen.common.BasePresenterProvider
import com.gmail.nigtime456.weatherapplication.screen.savedlocations.list.ItemPresenterFactory
import com.gmail.nigtime456.weatherapplication.screen.savedlocations.list.SavedLocationItemPresenter

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
            appContainer.forecastProvider,
            appContainer.settingsProvider,
            savedLocation
        )
    }
}