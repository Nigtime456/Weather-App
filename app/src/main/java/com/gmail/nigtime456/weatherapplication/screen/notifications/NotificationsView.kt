/*
 * Сreated by Igor Pokrovsky. 2020/5/23
 */

package com.gmail.nigtime456.weatherapplication.screen.notifications

import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation

interface NotificationsView {
    fun showProgressLayout()

    fun submitList(locations: List<SavedLocation>)
}