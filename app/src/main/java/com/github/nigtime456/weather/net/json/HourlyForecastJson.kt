/*
 * Сreated by Igor Pokrovsky. 2020/6/7
 */

package com.github.nigtime456.weather.net.json

import com.google.gson.annotations.SerializedName

data class HourlyForecastJson(
    @SerializedName("data")
    val forecast: List<Data>
) {

    data class Data(
        @SerializedName("time")
        val time: Long, // 1591542000
        @SerializedName("summary")
        val summary: String, // Сильная Облачность
        @SerializedName("icon")
        val icon: String, // cloudy
        @SerializedName("precipProbability")
        val precipProbability: Double, // 0
        @SerializedName("precipType")
        val precipType: String, // rain
        @SerializedName("temperature")
        val temperature: Double // 14.15
    )
}