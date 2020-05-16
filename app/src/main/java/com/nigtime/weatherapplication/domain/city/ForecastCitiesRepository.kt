/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.nigtime.weatherapplication.domain.city

import io.reactivex.Flowable

interface ForecastCitiesRepository {
    /**
     * Получить города только с именем и идом.
     * [CityForForecast]
     */
    fun getListCities(): Flowable<List<CityForForecast>>
}