/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.github.nigtime456.weather.net.service

import com.github.nigtime456.weather.BuildConfig
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherApi {

    @GET("/forecast/${BuildConfig.DARK_SKY_API_KEY}/{lat},{lon}?exclude=minutely,flags&units=si&lang=ru")
    fun getForecast(
        @Path("lat") lat: Double,
        @Path("lon") lon: Double
    ): Call<ResponseBody>
}