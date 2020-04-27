/*
 * Сreated by Igor Pokrovsky. 2020/4/26
 */

package com.nigtime.weatherapplication.db.data

import android.os.Parcel
import android.os.Parcelable

data class CityForForecastData(
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
        val CREATOR: Parcelable.Creator<CityForForecastData> =
            object : Parcelable.Creator<CityForForecastData> {
                override fun createFromParcel(source: Parcel): CityForForecastData =
                    CityForForecastData(source)

                override fun newArray(size: Int): Array<CityForForecastData?> = arrayOfNulls(size)
            }
    }
}
