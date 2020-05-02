/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.domain.settings

sealed class TempUnit {
    abstract fun convertJsonValue(temp: Double): Double

    object Kelvin : TempUnit() {
        override fun convertJsonValue(temp: Double): Double {
            return temp - 273.15
        }
    }

    object Fahrenheit : TempUnit() {
        override fun convertJsonValue(temp: Double): Double {
            return temp * 1.8 + 32
        }
    }

    object Celsius : TempUnit() {
        override fun convertJsonValue(temp: Double): Double {
            //по умолчанию - цельсий
            return temp
        }
    }
}