/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.domain.city

/**
 * Data класс представляющий собой город, сохраненный в таблице
 *
 */
open class WishCity constructor(
    val cityId: Long,
    val listIndex: Int,
    val name: String,
    val stateName: String,
    val countryName: String
) {
    companion object {
        const val NO_LIST_INDEX = -1
    }

    fun getStateAndCounty(): CharSequence {
        return if (stateName.isNotEmpty()) {
            if (countryName.isNotEmpty()) {
                "$countryName, $stateName"
            } else {
                stateName
            }
        } else {
            countryName
        }
    }

    override fun toString(): String {
        return "SelectedCityData{cityId=$cityId, listIndex=$listIndex, name='$name', stateName='$stateName', countryName='$countryName'}"
    }
}