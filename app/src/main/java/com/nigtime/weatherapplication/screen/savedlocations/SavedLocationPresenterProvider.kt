/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.savedlocations

import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.di.AppContainer
import com.nigtime.weatherapplication.common.rx.RxDelayedMessageDispatcher
import com.nigtime.weatherapplication.screen.common.BasePresenterProvider

class SavedLocationPresenterProvider : BasePresenterProvider<SavedLocationsPresenter>() {
    override fun createPresenter(appContainer: AppContainer): SavedLocationsPresenter {
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

}