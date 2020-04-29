/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.net.service

import com.nigtime.weatherapplication.domain.forecast.CurrentForecast
import com.nigtime.weatherapplication.domain.params.RequestParams
import com.nigtime.weatherapplication.domain.repository.net.ForecastManager
import com.nigtime.weatherapplication.net.jsons.JsonCurrentForecast
import com.nigtime.weatherapplication.utility.Mapper
import io.reactivex.Single

class ForecastManagerImpl constructor(
    private val netSource: ForecastSource,
    private val currentMapper: Mapper<JsonCurrentForecast, CurrentForecast>
) : ForecastManager {

    override fun getCurrentForecast(params: RequestParams): Single<CurrentForecast> {
        return netSource.getCurrentForecastJson(params).map(currentMapper::map)
    }
}
