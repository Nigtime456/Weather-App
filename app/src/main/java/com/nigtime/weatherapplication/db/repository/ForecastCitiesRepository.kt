/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.nigtime.weatherapplication.db.repository

import com.nigtime.weatherapplication.db.data.CityForForecast
import io.reactivex.Single

interface ForecastCitiesRepository {
    /**
     * Получить города только с именем и идом.
     * [CityForForecast]
     */
    fun getListCityForForecast(): Single<List<CityForForecast>>
}