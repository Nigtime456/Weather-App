/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.github.nigtime456.weather.data.location

data class SearchCity constructor(
    val id: Long,
    val lat: Double,
    val lon: Double,
    val name: String,
    val state: String,
    val country: String,
    val isSaved: Boolean,
    val query: String
) {

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