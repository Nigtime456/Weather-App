/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.gmail.nigtime456.weatherapplication.net.json

import com.google.gson.annotations.SerializedName

data class JsonHourlyData(
    @SerializedName("temp")
    val temp: Double, // 1.9
    @SerializedName("timestamp_local")
    val timeStamp: String,  //"2020-02-22T21:00:00"
    @SerializedName("app_temp")
    val appTemp: String, // -2.6
    @SerializedName("pres")
    val pres: String, // 982.478
    @SerializedName("wind_spd")
    val windSpd: String, // 1.63089
    @SerializedName("wind_cdir")
    val windCdir: String, // З
    @SerializedName("rh")
    val rh: String, // 93
    @SerializedName("pop")
    val probabilityOfPrecipitation: Int, //?
    @SerializedName("weather")
    val weather: JsonWeather
)