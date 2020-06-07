/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

package com.gmail.nigtime456.weatherapplication.net.mappers

import com.gmail.nigtime456.weatherapplication.data.forecast.Wind
import com.gmail.nigtime456.weatherapplication.net.json.JsonCurrentData
import javax.inject.Inject

class WindMapper @Inject constructor(){

    fun map(jsonCurrentData: JsonCurrentData): Wind {
        return Wind(
            jsonCurrentData.windSped,
            jsonCurrentData.windDirectionDegrees,
            Wind.CardinalDirection.fromDegrees(jsonCurrentData.windDirectionDegrees)
        )
    }
}