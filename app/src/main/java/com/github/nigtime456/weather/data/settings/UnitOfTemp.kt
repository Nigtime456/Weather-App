/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.github.nigtime456.weather.data.settings

sealed class UnitOfTemp {

    companion object {
        fun getByCode(code: String): UnitOfTemp {
            return when (code) {
                "cel" -> Celsius
                "fah" -> Fahrenheit
                "kel" -> Kelvin
                else -> error("invalid code = $code")
            }
        }
    }

    abstract fun convert(celsius: Double): Double

    object Kelvin : UnitOfTemp() {
        override fun convert(celsius: Double): Double {
            return celsius + 273.15
        }
    }

    object Fahrenheit : UnitOfTemp() {
        override fun convert(celsius: Double): Double {
            return celsius * 1.8 + 32
        }
    }

    object Celsius : UnitOfTemp() {
        override fun convert(celsius: Double): Double {
            //по умолчанию - цельсий
            return celsius
        }
    }
}