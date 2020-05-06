/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.domain.forecast

import androidx.annotation.DrawableRes

open class Weather constructor(val temp: Double, @DrawableRes val ico: Int) {

    override fun toString(): String {
        return "Forecast(temp=$temp, ico=$ico)"
    }
}