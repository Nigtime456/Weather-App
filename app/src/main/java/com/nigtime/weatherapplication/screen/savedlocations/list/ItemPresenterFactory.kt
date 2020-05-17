/*
 * Сreated by Igor Pokrovsky. 2020/5/18
 */

package com.nigtime.weatherapplication.screen.savedlocations.list

import com.nigtime.weatherapplication.domain.location.SavedLocation

interface ItemPresenterFactory {
    fun getItemPresenter(savedLocation: SavedLocation): SavedLocationItemPresenter
}