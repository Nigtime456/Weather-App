/*
 * Сreated by Igor Pokrovsky. 2020/6/7
 */

package com.github.nigtime456.weather.net.json

import com.google.gson.annotations.SerializedName

data class ForecastJson(
    @SerializedName("latitude")
    val latitude: Double, // 43.105621
    @SerializedName("longitude")
    val longitude: Double, // 131.873535
    @SerializedName("timezone")
    val timezone: String, // Asia/Vladivostok
    @SerializedName("currently")
    val currently: CurrentlyForecastJson,
    @SerializedName("hourly")
    val hourly: HourlyForecastJson,
    @SerializedName("daily")
    val daily: DailyForecastJson
)