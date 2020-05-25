/*
 * Сreated by Igor Pokrovsky. 2020/5/9
 */

package com.gmail.nigtime456.weatherapplication.domain.settings

import androidx.annotation.StringRes
import com.gmail.nigtime456.weatherapplication.R

sealed class UnitOfPressure {

    companion object {
        fun getByCode(code: String): UnitOfPressure {
            return when (code) {
                "mbar" -> MBar
                "mm_hg" -> MillimeterOfMercury
                else -> error("invalid code = $code")
            }
        }
    }

    abstract fun convert(mBar: Double): Double

    @StringRes
    abstract fun getFormattingPattern(): Int


    object MBar : UnitOfPressure() {
        override fun convert(mBar: Double): Double = mBar

        override fun getFormattingPattern(): Int = R.string.units_mbar_f
    }

    object MillimeterOfMercury : UnitOfPressure() {
        override fun convert(mBar: Double): Double = mBar * 0.75

        override fun getFormattingPattern(): Int = R.string.units_mm_hg_f

    }
}