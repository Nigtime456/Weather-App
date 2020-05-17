/*
 * Сreated by Igor Pokrovsky. 2020/5/17
 */

package com.nigtime.weatherapplication.screen.savedlocations.list

import androidx.annotation.DrawableRes
import com.nigtime.weatherapplication.domain.settings.UnitOfTemp

interface SavedLocationItemView {
    fun showCurrentTemp(temp: Double, unitOfTemp: UnitOfTemp)
    fun showCurrentTempIco(@DrawableRes ico: Int)
}