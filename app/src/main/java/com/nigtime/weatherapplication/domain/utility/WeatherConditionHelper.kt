/*
 * Сreated by Igor Pokrovsky. 2020/5/10
 */


package com.nigtime.weatherapplication.domain.utility

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.nigtime.weatherapplication.R

/**
 * https://www.weatherbit.io/api/codes
 */

object WeatherConditionHelper {


    @StringRes
    fun getDescriptionByCode(code: Int): Int {
        return when (code) {
            200 -> R.string.weather_description_200
            201 -> R.string.weather_description_201
            202 -> R.string.weather_description_202
            230 -> R.string.weather_description_230
            231 -> R.string.weather_description_231
            232 -> R.string.weather_description_232
            233 -> R.string.weather_description_233
            300 -> R.string.weather_description_300
            301 -> R.string.weather_description_301
            302 -> R.string.weather_description_302
            500 -> R.string.weather_description_500
            501 -> R.string.weather_description_501
            502 -> R.string.weather_description_502
            511 -> R.string.weather_description_511
            520 -> R.string.weather_description_520
            521 -> R.string.weather_description_521
            522 -> R.string.weather_description_522
            600 -> R.string.weather_description_600
            601 -> R.string.weather_description_601
            602 -> R.string.weather_description_602
            610 -> R.string.weather_description_610
            611 -> R.string.weather_description_611
            612 -> R.string.weather_description_612
            621 -> R.string.weather_description_621
            622 -> R.string.weather_description_622
            623 -> R.string.weather_description_623
            700 -> R.string.weather_description_700
            711 -> R.string.weather_description_711
            721 -> R.string.weather_description_721
            731 -> R.string.weather_description_731
            741 -> R.string.weather_description_741
            751 -> R.string.weather_description_751
            800 -> R.string.weather_description_800
            801 -> R.string.weather_description_801
            802 -> R.string.weather_description_802
            803 -> R.string.weather_description_803
            804 -> R.string.weather_description_804
            900 -> R.string.weather_description_900
            else -> error("unknown weather code = $code ?")
        }
    }

    //TODO доделать: по коду выбирается икона
    @DrawableRes
    fun getIconByCode(code: Int): Int {
        return R.drawable.ic_011_storm_2
    }
}