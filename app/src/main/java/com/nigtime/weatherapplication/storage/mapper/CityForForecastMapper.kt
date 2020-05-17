/*
 * Сreated by Igor Pokrovsky. 2020/5/2
 */

package com.nigtime.weatherapplication.storage.mapper

import com.nigtime.weatherapplication.domain.city.CityForForecast

class CityForForecastMapper {

    fun map(cityId: Long, cityName: String): CityForForecast {
        return CityForForecast(cityId, cityName)
    }
}