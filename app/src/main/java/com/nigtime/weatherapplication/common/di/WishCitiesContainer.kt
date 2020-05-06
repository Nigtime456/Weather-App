/*
 * Сreated by Igor Pokrovsky. 2020/5/6
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/6
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/6
 */

package com.nigtime.weatherapplication.common.di

import android.content.Context
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.rx.RxDelayedMessageDispatcher
import com.nigtime.weatherapplication.screens.wishlist.WishCitiesPresenter

class WishCitiesContainer constructor(context: Context, appContainer: AppContainer) {
    val presenter: WishCitiesPresenter
    val removeDelay = context.resources.getInteger(R.integer.wish_remove_delay)

    init {
        val messageDispatcher =
            RxDelayedMessageDispatcher(removeDelay.toLong(), appContainer.schedulerProvider)
        presenter =
            WishCitiesPresenter(
                appContainer.schedulerProvider,
                appContainer.wishCityRepository,
                messageDispatcher
            )
    }
}