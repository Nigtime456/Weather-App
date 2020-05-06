/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.domain.forecast

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.nigtime.weatherapplication.R

object WeatherInfoHelper {

    @StringRes
    fun getDescriptionByCode(code: Int): Int {
        return R.string.hello_blank_fragment
    }

    @DrawableRes
    fun getIconByCode(code: Int): Int {
        return R.drawable._weather_stub
    }
}