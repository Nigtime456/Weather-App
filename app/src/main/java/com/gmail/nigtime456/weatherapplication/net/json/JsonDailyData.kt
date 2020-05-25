/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.gmail.nigtime456.weatherapplication.net.json

import com.google.gson.annotations.SerializedName

data class JsonDailyData(
    @SerializedName("temp")
    val temp: String, // -2.9
    @SerializedName("max_temp")
    val maxTemp: Double, // -0.8
    @SerializedName("min_temp")
    val minTemp: Double, // -4.3
    @SerializedName("valid_date")
    val date: String,//2020-02-22
    @SerializedName("pres")
    val pressure: String, // 986.867
    @SerializedName("clouds")
    val cloudsPercent: String, // 51
    @SerializedName("wind_spd")
    val windSped: String, // 4.98183
    @SerializedName("wind_cdir")
    val windDirection: String, // ЮЮЗ
    @SerializedName("wind_cdir_full")
    val windDirectionFull: String, // Юго-Юго-Западный
    @SerializedName("wind_dir")
    val windDirectionAngle: String, // 203
    @SerializedName("rh")
    val averageHumidity: String, // 80
    @SerializedName("vis")
    val visibility: String, // 0
    @SerializedName("sunrise_ts")
    val sunriseTs: Int, // 1581311052
    @SerializedName("sunset_ts")
    val sunsetTs: Int, // 1581344827
    @SerializedName("snow")
    val snow: String, // 0.0268709
    @SerializedName("uv")
    val uv: String, // 2.3729
    @SerializedName("pop")
    val pop: String, // 20
    @SerializedName("weather")
    val weather: JsonWeather
)