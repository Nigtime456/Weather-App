/*
 * Сreated by Igor Pokrovsky. 2020/6/17
 */

package com.github.nigtime456.weather.data.forecast

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class PartOfDay : Parcelable {
    DAY, NIGHT;
}