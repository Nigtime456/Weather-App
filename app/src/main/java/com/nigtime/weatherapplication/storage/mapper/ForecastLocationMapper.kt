/*
 * Сreated by Igor Pokrovsky. 2020/5/2
 */

package com.nigtime.weatherapplication.storage.mapper

import com.nigtime.weatherapplication.domain.location.ForecastLocation


class ForecastLocationMapper {

    fun map(id: Long, name: String): ForecastLocation {
        return ForecastLocation.City(id, name)
    }
}