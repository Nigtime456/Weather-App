/*
 * Сreated by Igor Pokrovsky. 2020/4/26
 */

package com.nigtime.weatherapplication.domain.city

import android.os.Parcel
import android.os.Parcelable

data class CityForForecast(
    val cityId: Long,
    val cityName: String
) : Parcelable {

    constructor(source: Parcel) : this(
        source.readLong(),
        source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(cityId)
        writeString(cityName)
    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<CityForForecast> =
            object : Parcelable.Creator<CityForForecast> {
                override fun createFromParcel(source: Parcel): CityForForecast =
                    CityForForecast(source)

                override fun newArray(size: Int): Array<CityForForecast?> = arrayOfNulls(size)
            }
    }
}
