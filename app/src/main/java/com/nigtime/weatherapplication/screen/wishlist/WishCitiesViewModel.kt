/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.wishlist

import androidx.lifecycle.ViewModel
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.App
import com.nigtime.weatherapplication.common.rx.RxDelayedMessageDispatcher
import com.nigtime.weatherapplication.screen.common.PresenterProvider

class WishCitiesViewModel : ViewModel(), PresenterProvider<WishCitiesPresenter> {
    private val presenter: WishCitiesPresenter

    init {
        val removeDelay = App.INSTANCE.resources.getInteger(R.integer.wish_remove_delay)
        val appContainer = App.INSTANCE.appContainer
        val messageDispatcher =
            RxDelayedMessageDispatcher(removeDelay.toLong(), appContainer.schedulerProvider)
        presenter = WishCitiesPresenter(
            appContainer.schedulerProvider,
            appContainer.wishCityRepository,
            messageDispatcher
        )
    }

    override fun providePresenter(): WishCitiesPresenter = presenter
}