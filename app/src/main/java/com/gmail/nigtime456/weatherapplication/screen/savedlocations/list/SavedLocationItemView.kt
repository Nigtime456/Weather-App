/*
 * Сreated by Igor Pokrovsky. 2020/5/17
 */

package com.gmail.nigtime456.weatherapplication.screen.savedlocations.list

import androidx.annotation.DrawableRes
import com.gmail.nigtime456.weatherapplication.domain.settings.UnitOfTemp

interface SavedLocationItemView {
    fun showCurrentTemp(temp: Double, unitOfTemp: UnitOfTemp)
    fun showCurrentTempIco(@DrawableRes ico: Int)
}