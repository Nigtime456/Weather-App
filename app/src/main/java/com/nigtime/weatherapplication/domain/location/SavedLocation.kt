/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.domain.location


sealed class SavedLocation constructor(val listIndex: Int) {

    abstract fun getKey(): Long
    abstract fun getName(): String
    abstract fun getDescription(): String
    abstract fun areItemsTheSame(other: SavedLocation): Boolean
    abstract fun areContentsTheSame(other: SavedLocation): Boolean


    @Suppress("MemberVisibilityCanBePrivate")
    class City(
        listIndex: Int,
        val cityId: Long,
        val cityName: String,
        val stateName: String,
        val countryName: String
    ) : SavedLocation(listIndex) {

        override fun getKey(): Long {
            return cityId
        }


        override fun getName(): String {
            return cityName
        }

        override fun getDescription(): String {
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

        override fun areItemsTheSame(other: SavedLocation): Boolean {
            if (other !is City)
                return false

            return cityId == other.cityId
        }

        override fun areContentsTheSame(other: SavedLocation): Boolean {
            if (other !is City)
                return false

            return cityId == other.cityId && listIndex == other.listIndex
        }

    }
}
