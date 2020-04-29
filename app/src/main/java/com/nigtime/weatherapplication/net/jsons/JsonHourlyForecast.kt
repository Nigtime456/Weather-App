/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.jsons

import com.google.gson.annotations.SerializedName

/**
 * Часовой прогноз/прогноз по часам
 */
data class JsonHourlyForecast(
    @SerializedName("data")
    val forecastList: List<JsonHourlyData>,
    @SerializedName("city_name")
    val cityName: String,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("pop")
    val pop: String
)