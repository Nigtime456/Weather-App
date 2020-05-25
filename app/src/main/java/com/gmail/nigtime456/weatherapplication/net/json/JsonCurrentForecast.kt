/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.gmail.nigtime456.weatherapplication.net.json

import com.google.gson.annotations.SerializedName

/**
 * Текущий прогноз
 */
data class JsonCurrentForecast(
    @SerializedName("data")
    val forecastList: List<JsonCurrentData>
)