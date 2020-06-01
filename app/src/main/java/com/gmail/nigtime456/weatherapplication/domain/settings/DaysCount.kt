/*
 * Сreated by Igor Pokrovsky. 2020/6/1
 */

package com.gmail.nigtime456.weatherapplication.domain.settings

import com.gmail.nigtime456.weatherapplication.R

enum class DaysCount {
    FIVE, TEN, SIXTEEN;

    fun getCount(): Int = when (this) {
        FIVE -> 5
        TEN -> 10
        SIXTEEN -> 16
    }

    fun getId(): Int = when (this) {
        FIVE -> R.id.currentForecastDisplay5days
        TEN -> R.id.currentForecastDisplay10days
        SIXTEEN -> R.id.currentForecastDisplay16days
    }

    fun getCode(): String = when (this) {
        FIVE -> "5_days"
        TEN -> "10_days"
        SIXTEEN -> "16_days"
    }

    companion object {
        fun getDefaultKey(): String = FIVE.getCode()

        fun getById(id: Int): DaysCount {
            return when (id) {
                R.id.currentForecastDisplay5days -> FIVE
                R.id.currentForecastDisplay10days -> TEN
                R.id.currentForecastDisplay16days -> SIXTEEN
                else -> error("invalid id = $id")
            }
        }

        fun getByCode(code: String): DaysCount {
            return when (code) {
                FIVE.getCode() -> FIVE
                TEN.getCode() -> TEN
                SIXTEEN.getCode() -> SIXTEEN
                else -> error("invalid code = $code")
            }
        }
    }

}