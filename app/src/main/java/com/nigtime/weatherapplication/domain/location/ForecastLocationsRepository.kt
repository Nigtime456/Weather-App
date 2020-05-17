/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.nigtime.weatherapplication.domain.location

import io.reactivex.Flowable

interface ForecastLocationsRepository {
    /**
     * Получить города только с именем и идом.
     * [ForecastLocation]
     */
    fun getLocations(): Flowable<List<ForecastLocation>>
}