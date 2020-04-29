/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.service

import com.nigtime.weatherapplication.net.jsons.JsonCurrentForecast
import com.nigtime.weatherapplication.net.jsons.JsonDailyForecast
import com.nigtime.weatherapplication.net.jsons.JsonHourlyForecast
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * https://www.weatherbit.io/api
 */

interface WeatherApi {
    @GET("/v2.0/current")
    fun currentForecast(@QueryMap params: Map<String, String>): Single<JsonCurrentForecast>

    @GET("/v2.0/forecast/daily")
    fun dailyForecast(@QueryMap params: Map<String, String>): Single<JsonDailyForecast>

    @GET("/v2.0/forecast/hourly")
    fun hourlyForecast(@QueryMap params: Map<String, String>): Single<JsonHourlyForecast>
}