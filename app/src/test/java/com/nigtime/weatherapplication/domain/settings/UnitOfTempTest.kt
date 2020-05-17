/*
 * Сreated by Igor Pokrovsky. 2020/5/16
 */

package com.nigtime.weatherapplication.domain.settings

import org.junit.Test

class UnitOfTempTest {

    @Test
    fun test_fahrenheit() {
        val unitOfTemp = UnitOfTemp.Fahrenheit
        assert(48.2 == unitOfTemp.convertJsonValue(9.0)) {
            "conversion to fahrenheit  is incorrect"
        }
    }

    @Test
    fun test_celsius() {
        val unitOfTemp = UnitOfTemp.Celsius
        assert(40.0 == unitOfTemp.convertJsonValue(40.0)) {
            "conversion to celsius  is incorrect"
        }
    }

    @Test
    fun test_kelvin() {
        val unitOfTemp = UnitOfTemp.Kelvin
        assert(273.15 == unitOfTemp.convertJsonValue(0.0)) {
            "conversion to kelvin  is incorrect"
        }
    }
}