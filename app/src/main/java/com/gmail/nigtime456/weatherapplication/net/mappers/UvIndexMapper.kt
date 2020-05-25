/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

package com.gmail.nigtime456.weatherapplication.net.mappers

import com.gmail.nigtime456.weatherapplication.domain.forecast.UvIndex
import com.gmail.nigtime456.weatherapplication.net.json.JsonCurrentData

class UvIndexMapper {

    fun map(jsonCurrentData: JsonCurrentData): UvIndex {
        return UvIndex(jsonCurrentData.uvIndex.toInt())
    }
}