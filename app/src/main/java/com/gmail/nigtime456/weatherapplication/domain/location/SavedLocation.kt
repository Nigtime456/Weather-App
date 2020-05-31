/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.gmail.nigtime456.weatherapplication.domain.location

import android.os.Parcel
import android.os.Parcelable
import com.gmail.nigtime456.weatherapplication.domain.net.RequestParams


sealed class SavedLocation constructor(val listIndex: Int) : Parcelable {

    abstract fun getKey(): Long
    abstract fun getName(): String
    abstract fun createRequestParams(): RequestParams
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
    ) : SavedLocation(listIndex), Parcelable {

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<City> = object : Parcelable.Creator<City> {
                override fun createFromParcel(source: Parcel): City = City(source)
                override fun newArray(size: Int): Array<City?> = arrayOfNulls(size)
            }
        }

        constructor(source: Parcel) : this(
            source.readInt(),
            source.readLong(),
            source.readString() ?: "",
            source.readString() ?: "",
            source.readString() ?: ""
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeInt(listIndex)
            writeLong(cityId)
            writeString(cityName)
            writeString(stateName)
            writeString(countryName)
        }

        override fun getKey(): Long {
            return cityId
        }

        override fun getName(): String {
            return cityName
        }

        override fun createRequestParams(): RequestParams {
            return RequestParams.City(cityId)
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

        override fun toString(): String {
            return "[$listIndex] $cityName"
        }
    }
}
