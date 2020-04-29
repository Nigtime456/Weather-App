/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.jsons

import com.google.gson.annotations.SerializedName

/**
 * Текущий прогноз
 */
data class JsonCurrentForecast(
    @SerializedName("data")
    val forecastList: List<JsonCurrentData>
)