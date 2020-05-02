/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.utility.testing

import com.nigtime.weatherapplication.domain.param.RequestParams
import com.nigtime.weatherapplication.net.json.JsonCurrentForecast
import com.nigtime.weatherapplication.net.json.JsonDailyForecast
import com.nigtime.weatherapplication.net.json.JsonHourlyForecast
import com.nigtime.weatherapplication.net.service.ForecastSource
import io.reactivex.Single

class FakeForecastSource : ForecastSource {

    override fun getJsonCurrentForecast(requestParams: RequestParams): Single<JsonCurrentForecast> {
        return Single.fromCallable { FakeJsonDataProvider.getCurrentForecast() }
    }

    override fun getJsonHourlyForecast(requestParams: RequestParams): Single<JsonHourlyForecast> {
        return Single.fromCallable { FakeJsonDataProvider.getHourlyForecast() }
    }

    override fun getJsonDailyForecast(requestParams: RequestParams): Single<JsonDailyForecast> {
        return Single.fromCallable { FakeJsonDataProvider.getDailyForecast() }
    }
}