/*
 * Сreated by Igor Pokrovsky. 2020/6/7
 */

package com.github.nigtime456.weather.net.json

import com.google.gson.annotations.SerializedName

data class DailyForecastJson(
    @SerializedName("data")
    val forecast: List<Data>
) {

    data class Data(
        @SerializedName("time")
        val time: Long, // 1591538400
        @SerializedName("summary")
        val summary: String, // Небольшая облачность в течение всего дня.
        @SerializedName("icon")
        val icon: String, // partly-cloudy-day
        @SerializedName("sunriseTime")
        val sunriseTime: Long, // 1591558500
        @SerializedName("sunsetTime")
        val sunsetTime: Long, // 1591613520
        @SerializedName("moonPhase")
        val moonPhase: Double, // 0.6
        @SerializedName("precipProbability")
        val precipProbability: Double, // 0.1
        @SerializedName("precipType")
        val precipType: String?, // rain
        @SerializedName("temperatureHigh")
        val temperatureHigh: Double, // 16.98
        @SerializedName("temperatureLow")
        val temperatureLow: Double, // 14.33
        @SerializedName("apparentTemperatureHigh")
        val apparentTemperatureHigh: Double, // 16.7
        @SerializedName("apparentTemperatureLow")
        val apparentTemperatureLow: Double, // 14.6
        @SerializedName("dewPoint")
        val dewPoint: Double, // 12.64
        @SerializedName("humidity")
        val humidity: Double, // 0.84
        @SerializedName("pressure")
        val pressure: Double, // 1012.2
        @SerializedName("windSpeed")
        val windSpeed: Double, // 5.18
        @SerializedName("windGust")
        val windGust: Double, // 8.81
        @SerializedName("windBearing")
        val windBearing: Int, // 158
        @SerializedName("cloudCover")
        val cloudCover: Double, // 0.62
        @SerializedName("uvIndex")
        val uvIndex: Int, // 7
        @SerializedName("visibility")
        val visibility: Double, // 15.384
        @SerializedName("ozone")
        val ozone: Double // 334
    )
}