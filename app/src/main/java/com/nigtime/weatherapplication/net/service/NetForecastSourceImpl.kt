/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.service

import com.nigtime.weatherapplication.domain.params.RequestParams
import com.nigtime.weatherapplication.net.jsons.JsonCurrentForecast
import com.nigtime.weatherapplication.net.jsons.JsonDailyForecast
import com.nigtime.weatherapplication.net.jsons.JsonHourlyForecast
import io.reactivex.Single

class NetForecastSourceImpl constructor(
    private val weatherApi: WeatherApi
) : ForecastSource {

    override fun getCurrentForecastJson(requestParams: RequestParams): Single<JsonCurrentForecast> {
        return weatherApi.currentForecast(makeQueryParams(requestParams))
    }

    override fun getHourlyForecastJson(requestParams: RequestParams): Single<JsonHourlyForecast> {
        return weatherApi.hourlyForecast(makeQueryParams(requestParams))
    }

    override fun getDailyForecastJson(requestParams: RequestParams): Single<JsonDailyForecast> {
        return weatherApi.dailyForecast(makeQueryParams(requestParams))
    }

    private fun makeQueryParams(requestParams: RequestParams): Map<String, String> {
        val queryParams = mutableMapOf<String, String>()
        queryParams["lang"] = requestParams.lang.name
        //TODO вынести в конфиг
        queryParams["key"] = "e22b24a04735440fa35f60a070808e21"
        when (requestParams) {
            is RequestParams.CityParams -> {
                queryParams["city_id"] = requestParams.cityId.toString()
            }
        }
        return queryParams
    }


}