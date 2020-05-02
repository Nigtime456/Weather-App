/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.nigtime.weatherapplication.domain.repository

import com.nigtime.weatherapplication.domain.city.CityForForecast
import io.reactivex.Single

interface ForecastCitiesRepository {
    /**
     * Получить города только с именем и идом.
     * [CityForForecast]
     */
    fun getListCities(): Single<List<CityForForecast>>
}