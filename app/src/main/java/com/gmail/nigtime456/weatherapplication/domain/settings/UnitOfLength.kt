/*
 * Сreated by Igor Pokrovsky. 2020/5/9
 */

package com.gmail.nigtime456.weatherapplication.domain.settings

import androidx.annotation.StringRes
import com.gmail.nigtime456.weatherapplication.R

sealed class UnitOfLength {

    companion object {
        fun getByCode(code: String): UnitOfLength {
            return when (code) {
                "km" -> Kilometre
                "mi" -> Miles
                else -> error("invalid code = $code")
            }
        }
    }

    abstract fun convert(km: Double): Double

    @StringRes
    abstract fun getFormattingPattern(): Int

    object Kilometre : UnitOfLength() {
        override fun convert(km: Double): Double = km

        override fun getFormattingPattern(): Int = R.string.units_km_f
    }

    object Miles : UnitOfLength() {
        override fun convert(km: Double): Double = km / 1.61

        override fun getFormattingPattern(): Int = R.string.units_mi_f
    }
}