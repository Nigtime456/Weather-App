/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

package com.gmail.nigtime456.weatherapplication.net.service

import retrofit2.http.GET
import retrofit2.http.Headers

interface GeoSearchApi {

    @Headers("User-Agent: AndroidApp/com.gmail.nigtime456.weatherapplication")
    @GET
    fun findLocation()
}