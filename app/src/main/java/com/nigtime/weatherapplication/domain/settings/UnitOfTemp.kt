/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.domain.settings

sealed class UnitOfTemp {
    abstract fun convertJsonValue(celsius: Double): Double

    object Kelvin : UnitOfTemp() {
        override fun convertJsonValue(celsius: Double): Double {
            return celsius - 273.15
        }
    }

    object Fahrenheit : UnitOfTemp() {
        override fun convertJsonValue(celsius: Double): Double {
            return celsius * 1.8 + 32
        }
    }

    object Celsius : UnitOfTemp() {
        override fun convertJsonValue(celsius: Double): Double {
            //по умолчанию - цельсий
            return celsius
        }
    }
}