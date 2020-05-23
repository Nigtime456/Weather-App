/*
 * Сreated by Igor Pokrovsky. 2020/5/23
 */

package com.nigtime.weatherapplication.screen.notifications

import com.nigtime.weatherapplication.domain.location.SavedLocation

interface NotificationsView {
    fun showProgressLayout()

    fun submitList(locations: List<SavedLocation>)
}