/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.json

import com.google.gson.annotations.SerializedName

/**
 * Прогноз по дням
 */
data class JsonDailyForecast(
    @SerializedName("data")
    val forecastList: List<JsonDailyData>,
    @SerializedName("city_name")
    val cityName: String,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("lat")
    val lat: Double
)