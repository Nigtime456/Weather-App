/*
 * Сreated by Igor Pokrovsky. 2020/5/5
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.gmail.nigtime456.weatherapplication.net.repository

import com.gmail.nigtime456.weatherapplication.domain.net.RequestParams
import com.gmail.nigtime456.weatherapplication.net.dto.NetData
import com.gmail.nigtime456.weatherapplication.net.json.JsonCurrentForecast
import com.gmail.nigtime456.weatherapplication.net.json.JsonDailyForecast
import com.gmail.nigtime456.weatherapplication.net.json.JsonHourlyForecast
import com.gmail.nigtime456.weatherapplication.net.service.WeatherApi
import io.reactivex.Observable

class NetForecastSourceImpl constructor(
    private val weatherApi: WeatherApi
) : ForecastSource {

    override fun getCurrentForecast(requestParams: RequestParams): Observable<NetData<JsonCurrentForecast>> {
        return weatherApi.currentForecast(makeQueryParams(requestParams))
            .map(mapToNetData())
    }

    override fun getHourlyForecast(requestParams: RequestParams): Observable<NetData<JsonHourlyForecast>> {
        return weatherApi.hourlyForecast(makeQueryParams(requestParams))
            .map(mapToNetData())
    }

    override fun getDailyForecast(requestParams: RequestParams): Observable<NetData<JsonDailyForecast>> {
        return weatherApi.dailyForecast(makeQueryParams(requestParams))
            .map(mapToNetData())
    }

    private fun makeQueryParams(requestParams: RequestParams): Map<String, String> {
        val queryParams = mutableMapOf<String, String>()
        //TODO вынести в конфиг
        queryParams["key"] = "e22b24a04735440fa35f60a070808e21"
        when (requestParams) {
            is RequestParams.City -> {
                queryParams["city_id"] = requestParams.cityId.toString()
            }
        }

        return queryParams
    }

    private fun <T> mapToNetData(): (T) -> NetData<T> = { data: T ->
        NetData(getTimestamp(), data)
    }

    private fun getTimestamp(): Long {
        return System.currentTimeMillis()
    }

}