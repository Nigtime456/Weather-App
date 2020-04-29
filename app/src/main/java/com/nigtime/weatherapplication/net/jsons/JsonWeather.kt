/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.jsons

import com.google.gson.annotations.SerializedName

/**
 * Описание погоды
 */
data class JsonWeather(
    //TODO не нужен ?
    @SerializedName("icon")
    val icon: String,
    @SerializedName("code")
    val code: Int,
    @SerializedName("description")
    val description: String
)