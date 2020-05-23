/*
 * Сreated by Igor Pokrovsky. 2020/5/23
 */

package com.nigtime.weatherapplication.screen.notifications

import com.nigtime.weatherapplication.screen.common.BasePresenterProvider

class NotificationsPresenterProvider : BasePresenterProvider<NotificationsPresenter>() {
    override fun createPresenter(): NotificationsPresenter {
        return NotificationsPresenter(appContainer.savedLocationsRepository)
    }
}