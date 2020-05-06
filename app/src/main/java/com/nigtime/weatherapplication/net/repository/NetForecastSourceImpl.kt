/*
 * Сreated by Igor Pokrovsky. 2020/5/5
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.repository

import com.nigtime.weatherapplication.domain.common.NetData
import com.nigtime.weatherapplication.domain.param.RequestParams
import com.nigtime.weatherapplication.net.json.JsonCurrentForecast
import com.nigtime.weatherapplication.net.json.JsonDailyForecast
import com.nigtime.weatherapplication.net.json.JsonHourlyForecast
import com.nigtime.weatherapplication.net.service.WeatherApi
import io.reactivex.Observable
import io.reactivex.Single

class NetForecastSourceImpl constructor(
    private val weatherApi: WeatherApi
) : ForecastSource {

    override fun getJsonCurrentForecast(requestParams: RequestParams): Observable<NetData<JsonCurrentForecast>> {
        return weatherApi.currentForecast(makeQueryParams(requestParams))
            .map(mapToNetData())
    }

    override fun getJsonHourlyForecast(requestParams: RequestParams): Observable<NetData<JsonHourlyForecast>> {
        return weatherApi.hourlyForecast(makeQueryParams(requestParams))
            .map(mapToNetData())
    }

    override fun getJsonDailyForecast(requestParams: RequestParams): Observable<NetData<JsonDailyForecast>> {
        return weatherApi.dailyForecast(makeQueryParams(requestParams))
            .map(mapToNetData())
    }

    private fun makeQueryParams(requestParams: RequestParams): Map<String, String> {
        val queryParams = mutableMapOf<String, String>()
        //TODO вынести в конфиг
        queryParams["key"] = "e22b24a04735440fa35f60a070808e21"
        when (requestParams) {
            is RequestParams.CityParams -> {
                queryParams["city_id"] = requestParams.cityId.toString()
            }
        }
        return queryParams
    }

    private fun <T> mapToNetData(): (T) -> NetData<T> = { data: T -> NetData(data, getTimestamp()) }

    private fun getTimestamp(): Long {
        return System.currentTimeMillis()
    }

}