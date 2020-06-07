/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.gmail.nigtime456.weatherapplication.data.location

import android.os.Parcel
import android.os.Parcelable
import com.gmail.nigtime456.weatherapplication.data.net.RequestParams

sealed class SavedLocation constructor(
    val listIndex: Int
) : Parcelable {

    abstract fun getKey(): Long
    abstract fun getName(): String
    abstract fun createRequestParams(): RequestParams
    abstract fun getDescription(): String

    @Suppress("MemberVisibilityCanBePrivate")
    class City(
        listIndex: Int,
        val cityId: Long,
        val cityName: String,
        val stateName: String,
        val countryName: String
    ) : SavedLocation(listIndex) {

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

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as City

            if (cityId != other.cityId) return false
            if (cityName != other.cityName) return false
            if (stateName != other.stateName) return false
            if (countryName != other.countryName) return false

            return true
        }

        override fun hashCode(): Int {
            var result = cityId.hashCode()
            result = 31 * result + cityName.hashCode()
            result = 31 * result + stateName.hashCode()
            result = 31 * result + countryName.hashCode()
            return result
        }

        override fun toString(): String {
            return "[$listIndex] $cityName"
        }
    }
}
