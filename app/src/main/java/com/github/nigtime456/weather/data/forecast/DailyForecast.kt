/*
 * Сreated by Igor Pokrovsky. 2020/6/13
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.github.nigtime456.weather.data.forecast

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DailyForecast(
    val timeZone: String,
    val timeMs: Long,
    val dayIndex: Int,
    val weatherCode: String,
    val tempHigh: Double,
    val tempLow: Double,
    val dewPoint: Double,
    val humidity: Double,
    val pressure: Double,
    val windSpeed: Double,
    val windGust: Double,
    val windDegrees: Int,
    val cloudCoverage: Double,
    val uvIndex: Int,
    val visibility: Double,
    val moonPhase: Double,
    val sunrise: Long,
    val sunset: Long
) : Parcelable