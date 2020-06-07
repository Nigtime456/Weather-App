/*
 * Сreated by Igor Pokrovsky. 2020/5/9
 */

package com.gmail.nigtime456.weatherapplication.data.forecast

import androidx.annotation.StringRes
import com.gmail.nigtime456.weatherapplication.R

data class AirQuality constructor(val index: Int) {

    @StringRes
    fun getFormattingString(): Int = when (index) {
        in 0..50 -> R.string.air_quality_good_f
        in 51..100 -> R.string.air_quality_moderate_f
        in 101..150 -> R.string.air_quality_unhealthy_sens_f
        in 151..200 -> R.string.air_quality_unhealthy_f
        in 201..300 -> R.string.air_quality_very_unhealthy_f
        in 301..500 -> R.string.air_quality_hazardous_f
        else -> error("invalid air quality index $index")
    }
}