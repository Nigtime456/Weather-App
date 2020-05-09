/*
 * Сreated by Igor Pokrovsky. 2020/5/9
 */

package com.nigtime.weatherapplication.domain.settings

import androidx.annotation.StringRes
import com.nigtime.weatherapplication.R

sealed class UnitOfPressure {
    abstract fun convert(mBar: Double): Double

    @StringRes
    abstract fun getFormattingPattern(): Int


    object MBar : UnitOfPressure() {
        override fun convert(mBar: Double): Double = mBar

        override fun getFormattingPattern(): Int = R.string.units_mbar_f

    }

}