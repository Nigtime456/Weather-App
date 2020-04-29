/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.nigtime.weatherapplication.domain.repository.cities

import com.nigtime.weatherapplication.domain.cities.CityForForecast
import io.reactivex.Single

interface ForecastCitiesRepository {
    /**
     * Получить города только с именем и идом.
     * [CityForForecast]
     */
    fun getListCities(): Single<List<CityForForecast>>
}