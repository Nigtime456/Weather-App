/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.service

import com.nigtime.weatherapplication.domain.weather.CurrentForecast
import com.nigtime.weatherapplication.domain.weather.DailyForecast
import com.nigtime.weatherapplication.domain.weather.HourlyForecast
import com.nigtime.weatherapplication.domain.param.RequestParams
import com.nigtime.weatherapplication.domain.repository.ForecastManager
import com.nigtime.weatherapplication.net.json.JsonCurrentForecast
import com.nigtime.weatherapplication.net.json.JsonDailyForecast
import com.nigtime.weatherapplication.net.json.JsonHourlyForecast
import com.nigtime.weatherapplication.net.mappers.CurrentForecastMapper
import com.nigtime.weatherapplication.net.mappers.DailyForecastMapper
import com.nigtime.weatherapplication.net.mappers.HourlyForecastMapper
import io.reactivex.Single

class ForecastManagerImpl constructor(
    private val netSource: ForecastSource,
    private val currentDataMapper: CurrentForecastMapper,
    private val hourlyDataMapper: HourlyForecastMapper,
    private val dailyDataMapper: DailyForecastMapper
) : ForecastManager {

    override fun getCurrentForecast(params: RequestParams): Single<CurrentForecast> {
        return netSource.getJsonCurrentForecast(params).map(currentDataMapper::map)
    }

    override fun getHourlyForecast(params: RequestParams): Single<HourlyForecast> {
        return netSource.getJsonHourlyForecast(params).map(hourlyDataMapper::map)
    }

    override fun getDailyForecast(params: RequestParams): Single<DailyForecast> {
        return netSource.getJsonDailyForecast(params).map(dailyDataMapper::map)
    }
}
