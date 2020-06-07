/*
 * Сreated by Igor Pokrovsky. 2020/5/9
 */

package com.gmail.nigtime456.weatherapplication.data.settings

import androidx.annotation.StringRes
import com.gmail.nigtime456.weatherapplication.R

sealed class UnitOfSpeed {

    companion object {
        fun getByCode(code: String): UnitOfSpeed {
            return when (code) {
                "kph" -> KilometrePerHour
                "mph" -> MilesPerHour
                "kn" -> Knots
                "ms" -> MetrePerSeconds
                else -> error("invalid code = $code")
            }
        }
    }

    abstract fun convert(metrePerSeconds: Double): Double

    @StringRes
    abstract fun getFormattingPattern(): Int


    object KilometrePerHour : UnitOfSpeed() {
        override fun convert(metrePerSeconds: Double): Double = metrePerSeconds * 3.6

        override fun getFormattingPattern(): Int = R.string.units_kph_f
    }

    object MilesPerHour : UnitOfSpeed() {
        override fun convert(metrePerSeconds: Double): Double = metrePerSeconds * 2.236
        override fun getFormattingPattern(): Int = R.string.units_mph_f
    }

    object Knots : UnitOfSpeed() {
        override fun convert(metrePerSeconds: Double): Double = metrePerSeconds * 1.94

        override fun getFormattingPattern(): Int = R.string.units_kn_f
    }

    object MetrePerSeconds : UnitOfSpeed() {
        override fun convert(metrePerSeconds: Double): Double = metrePerSeconds

        override fun getFormattingPattern(): Int = R.string.units_mps_f

    }

}