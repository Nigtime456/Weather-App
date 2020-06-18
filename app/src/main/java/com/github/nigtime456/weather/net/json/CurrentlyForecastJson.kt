/*
 * Сreated by Igor Pokrovsky. 2020/6/7
 */

package com.github.nigtime456.weather.net.json

import com.google.gson.annotations.SerializedName

data class CurrentlyForecastJson(
    @SerializedName("time")
    val time: Long, // 1591544133
    @SerializedName("summary")
    val summary: String, // Сильная Облачность
    @SerializedName("icon")
    val icon: String, // cloudy
    @SerializedName("temperature")
    val temperature: Double, // 14.03
    @SerializedName("apparentTemperature")
    val apparentTemperature: Double, // 14.03
    @SerializedName("dewPoint")
    val dewPoint: Double, // 10.96
    @SerializedName("humidity")
    val humidity: Double, // 0.82
    @SerializedName("pressure")
    val pressure: Double, // 1012.5
    @SerializedName("windSpeed")
    val windSpeed: Double, // 3.8
    @SerializedName("windGust")
    val windGust: Double, // 5.79
    @SerializedName("windBearing")
    val windBearing: Int, // 153
    @SerializedName("cloudCover")
    val cloudCover: Double, // 0.99
    @SerializedName("uvIndex")
    val uvIndex: Int, // 0
    @SerializedName("visibility")
    val visibility: Double, // 16.093
    @SerializedName("ozone")
    val ozone: Double // 346.9
)