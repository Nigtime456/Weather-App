/*
 * Сreated by Igor Pokrovsky. 2020/5/18
 */

package com.gmail.nigtime456.weatherapplication.screen.savedlocations.list

import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation

interface ItemPresenterFactory {
    fun getItemPresenter(savedLocation: SavedLocation): SavedLocationItemPresenter
}