/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

package com.gmail.nigtime456.weatherapplication.net.mappers

import com.gmail.nigtime456.weatherapplication.domain.forecast.AirQuality
import com.gmail.nigtime456.weatherapplication.net.json.JsonCurrentData

class AirQualityMapper {

    fun map(jsonCurrentData: JsonCurrentData): AirQuality {
        return AirQuality(jsonCurrentData.airQualityIndex)
    }
}