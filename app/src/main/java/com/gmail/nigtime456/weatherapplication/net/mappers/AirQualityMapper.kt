/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

package com.gmail.nigtime456.weatherapplication.net.mappers

import com.gmail.nigtime456.weatherapplication.data.forecast.AirQuality
import com.gmail.nigtime456.weatherapplication.net.json.JsonCurrentData
import javax.inject.Inject

class AirQualityMapper @Inject  constructor(){

    fun map(jsonCurrentData: JsonCurrentData): AirQuality {
        return AirQuality(jsonCurrentData.airQualityIndex)
    }
}