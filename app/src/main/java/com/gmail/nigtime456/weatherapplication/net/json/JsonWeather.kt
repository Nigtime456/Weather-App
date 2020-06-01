/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.gmail.nigtime456.weatherapplication.net.json

import com.google.gson.annotations.SerializedName

/**
 * Описание погоды
 */
data class JsonWeather(
    @SerializedName("icon")
    val icon: String,
    @SerializedName("code")
    val code: Int
)