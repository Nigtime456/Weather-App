/*
 * Сreated by Igor Pokrovsky. 2020/5/6
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.github.nigtime456.weather.data.repository

import com.github.nigtime456.weather.data.forecast.Forecast
import com.github.nigtime456.weather.data.location.SavedLocation
import io.reactivex.Observable

/**
 * Репозиторий предоставляющий погоду
 */
interface ForecastProvider {

    /**
     * @param forceNet - игнорировать кэш.
     */
    fun getForecast(location: SavedLocation, forceNet: Boolean = false): Observable<Forecast>

}