/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.github.nigtime456.weather.data.location

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SavedLocation constructor(
    val id: Long,
    val lat: Double,
    val lon: Double,
    val listIndex: Int,
    val name: String,
    val state: String,
    val country: String
) : Parcelable {

    fun getStateAndCounty(): CharSequence {
        return if (state.isNotEmpty()) {
            if (country.isNotEmpty()) {
                "$state, $country"
            } else {
                state
            }
        } else {
            country
        }
    }
}
