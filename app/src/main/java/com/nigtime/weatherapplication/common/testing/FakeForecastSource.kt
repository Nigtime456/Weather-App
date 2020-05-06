/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.common.testing

import com.nigtime.weatherapplication.domain.common.NetData
import com.nigtime.weatherapplication.domain.param.RequestParams
import com.nigtime.weatherapplication.net.json.JsonCurrentForecast
import com.nigtime.weatherapplication.net.json.JsonDailyForecast
import com.nigtime.weatherapplication.net.json.JsonHourlyForecast
import com.nigtime.weatherapplication.net.repository.ForecastSource
import io.reactivex.Observable
import io.reactivex.Single
import java.lang.Exception

class FakeForecastSource :
    ForecastSource {

    override fun getJsonCurrentForecast(requestParams: RequestParams): Observable<NetData<JsonCurrentForecast>> {
        return Observable.fromCallable { FakeJsonDataProvider.getCurrentForecast() }
            .map(mapToNetData())

    }

    override fun getJsonHourlyForecast(requestParams: RequestParams): Observable<NetData<JsonHourlyForecast>> {
        return Observable.fromCallable { FakeJsonDataProvider.getHourlyForecast() }
            .map(mapToNetData())
    }

    override fun getJsonDailyForecast(requestParams: RequestParams): Observable<NetData<JsonDailyForecast>> {
        return Observable.fromCallable { FakeJsonDataProvider.getDailyForecast() }
            .map(mapToNetData())
    }

    private fun <T> mapToNetData(): (T) -> NetData<T> = { data: T -> NetData(data, getTimestamp()) }

    private fun getTimestamp(): Long {
        return System.currentTimeMillis()
    }
}