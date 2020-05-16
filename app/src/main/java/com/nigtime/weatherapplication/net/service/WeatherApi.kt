/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.service

import com.nigtime.weatherapplication.net.json.JsonCurrentForecast
import com.nigtime.weatherapplication.net.json.JsonDailyForecast
import com.nigtime.weatherapplication.net.json.JsonHourlyForecast
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * https://www.weatherbit.io/api
 */

interface WeatherApi {
    @GET("/v2.0/current")
    fun currentForecast(@QueryMap params: Map<String, String>): Observable<JsonCurrentForecast>

    @GET("/v2.0/forecast/daily")
    fun dailyForecast(@QueryMap params: Map<String, String>): Observable<JsonDailyForecast>

    @GET("/v2.0/forecast/hourly")
    fun hourlyForecast(@QueryMap params: Map<String, String>): Observable<JsonHourlyForecast>
}