/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.gmail.nigtime456.weatherapplication.net.json

import com.google.gson.annotations.SerializedName

data class JsonCurrentData(
    @SerializedName("city_name")
    val cityName: String, // Moscow
    @SerializedName("lon")
    val lon: Double, // 37.61556
    @SerializedName("lat")
    val lat: Double, // 55.75222
    @SerializedName("temp")
    val temp: Double, // 3
    @SerializedName("app_temp")
    val feelsLikeTemp: Double, // -2.4
    @SerializedName("pres")
    val pressure: Double, // 979
    @SerializedName("wind_spd")
    val windSped: Double, // 8
    @SerializedName("wind_cdir_full")
    val windDirectionFull: String, // Западный
    @SerializedName("wind_cdir")
    val windDirection: String, // З
    @SerializedName("wind_dir")
    val windDirectionDegrees: Int, // 270
    @SerializedName("rh")
    val averageHumidity: Int, // 100
    @SerializedName("vis")
    val visibility: Double, // 1
    @SerializedName("sunrise")
    val sunrise: String, // 05:39
    @SerializedName("sunset")
    val sunset: String, // 13:43
    @SerializedName("snow")
    val snow: String, // 0
    @SerializedName("uv")
    val uvIndex: Double, // 0
    @SerializedName("precip")
    val precipitationRate: String, // 4.57895
    @SerializedName("aqi")
    val airQualityIndex: Int, // 13
    @SerializedName("dewpt")
    val dewPoint: Double,//15.65
    @SerializedName("clouds")
    val cloudsCoverage: Int,
    @SerializedName("weather")
    val weather: JsonWeather,
    @SerializedName("timezone")
    val timezone: String // Europe/Moscow
)