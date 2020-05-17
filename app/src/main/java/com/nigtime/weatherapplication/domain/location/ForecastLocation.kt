/*
 * Сreated by Igor Pokrovsky. 2020/5/17
 */

package com.nigtime.weatherapplication.domain.location

import android.os.Parcel
import android.os.Parcelable
import com.nigtime.weatherapplication.domain.params.RequestParams


sealed class ForecastLocation : Parcelable {

    abstract fun makeRequestParams(): RequestParams

    abstract fun getKey(): Long

    abstract fun getName(): String

    data class City(
        val cityId: Long,
        val cityName: String
    ) : ForecastLocation() {

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<City> =
                object : Parcelable.Creator<City> {
                    override fun createFromParcel(source: Parcel): City =
                        City(source)

                    override fun newArray(size: Int): Array<City?> = arrayOfNulls(size)
                }
        }

        constructor(source: Parcel) : this(
            source.readLong(),
            source.readString()!!
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeLong(cityId)
            writeString(cityName)
        }

        override fun makeRequestParams(): RequestParams {
            return RequestParams.City(cityId)
        }

        override fun getKey(): Long {
            return cityId
        }

        override fun getName(): String {
            return cityName
        }

    }

}