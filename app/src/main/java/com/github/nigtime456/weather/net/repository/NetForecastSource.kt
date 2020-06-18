/*
 * Сreated by Igor Pokrovsky. 2020/6/14
 */

package com.github.nigtime456.weather.net.repository

import com.github.nigtime456.weather.data.location.SavedLocation
import com.github.nigtime456.weather.net.dto.CachedData
import com.github.nigtime456.weather.net.service.WeatherApi
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class NetForecastSource @Inject constructor(
    private val weatherApi: WeatherApi
) : ForecastSource {

    override fun getForecastAsJson(location: SavedLocation): CachedData {
        val call = weatherApi.getForecast(location.lat, location.lon)
        val body = call.execute()

        return if (body.isSuccessful) {
            CachedData(getTimestamp(), getJson(body))
        } else {
            error("code = ${body.code()}, msg = ${body.message()} ")
        }
    }

    private fun getTimestamp() = System.currentTimeMillis()

    private fun getJson(body: Response<ResponseBody>): String {
        return body.body()?.string()
            ?: error("body == null. code = ${body.code()}, msg = ${body.message()}")
    }
}