/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.utility

import androidx.annotation.DrawableRes
import com.nigtime.weatherapplication.R

object WeatherIconHelper {
    @DrawableRes
    fun getIconByCode(code: Int): Int {
        return R.drawable._weather_stub
    }
}