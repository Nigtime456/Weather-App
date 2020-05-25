/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.gmail.nigtime456.weatherapplication.testing

import com.gmail.nigtime456.weatherapplication.domain.net.RequestParams
import com.gmail.nigtime456.weatherapplication.net.dto.NetData
import com.gmail.nigtime456.weatherapplication.net.json.JsonCurrentForecast
import com.gmail.nigtime456.weatherapplication.net.json.JsonDailyForecast
import com.gmail.nigtime456.weatherapplication.net.json.JsonHourlyForecast
import com.gmail.nigtime456.weatherapplication.net.repository.ForecastSource
import io.reactivex.Observable

class FakeForecastSource :
    ForecastSource {

    override fun getCurrentForecast(requestParams: RequestParams): Observable<NetData<JsonCurrentForecast>> {
        return Observable.fromCallable { FakeJsonDataProvider.getCurrentForecast() }
            .map(mapToNetData())

    }

    override fun getHourlyForecast(requestParams: RequestParams): Observable<NetData<JsonHourlyForecast>> {
        return Observable.fromCallable { FakeJsonDataProvider.getHourlyForecast() }
            .map(mapToNetData())
    }

    override fun getDailyForecast(requestParams: RequestParams): Observable<NetData<JsonDailyForecast>> {
        return Observable.fromCallable { FakeJsonDataProvider.getDailyForecast() }
            .map(mapToNetData())
    }

    private fun <T> mapToNetData(): (T) -> NetData<T> = { data: T ->
        NetData(getTimestamp(), data)
    }

    private fun getTimestamp(): Long {
        return System.currentTimeMillis()
    }
}