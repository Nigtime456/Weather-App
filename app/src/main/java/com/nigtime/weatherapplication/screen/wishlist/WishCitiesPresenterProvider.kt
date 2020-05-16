/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.wishlist

import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.di.AppContainer
import com.nigtime.weatherapplication.common.rx.RxDelayedMessageDispatcher
import com.nigtime.weatherapplication.screen.common.BasePresenterProvider

class WishCitiesPresenterProvider : BasePresenterProvider<WishCitiesPresenter>() {
    override fun createPresenter(appContainer: AppContainer): WishCitiesPresenter {
        val removeDelay = appContainer.appContext.resources.getInteger(R.integer.wish_remove_delay)
        val messageDispatcher =
            RxDelayedMessageDispatcher(removeDelay.toLong(), appContainer.schedulerProvider)

        return WishCitiesPresenter(
            appContainer.schedulerProvider,
            appContainer.wishCityRepository,
            messageDispatcher
        )
    }

}