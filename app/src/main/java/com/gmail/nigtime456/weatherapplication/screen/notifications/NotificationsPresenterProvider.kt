/*
 * Сreated by Igor Pokrovsky. 2020/5/23
 */

package com.gmail.nigtime456.weatherapplication.screen.notifications

import com.gmail.nigtime456.weatherapplication.screen.common.BasePresenterProvider

class NotificationsPresenterProvider : BasePresenterProvider<NotificationsPresenter>() {
    override fun createPresenter(): NotificationsPresenter {
        return NotificationsPresenter(appContainer.savedLocationsRepository)
    }
}