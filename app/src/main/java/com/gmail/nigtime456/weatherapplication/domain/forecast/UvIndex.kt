/*
 * Сreated by Igor Pokrovsky. 2020/5/9
 */

package com.gmail.nigtime456.weatherapplication.domain.forecast

import androidx.annotation.StringRes
import com.gmail.nigtime456.weatherapplication.R

data class UvIndex constructor(val index: Int) {
    @StringRes
    fun getFormattingString(): Int = when (index) {
        in 0..2 -> R.string.uv_index_low_f
        in 3..5 -> R.string.uv_index_moderate_f
        in 6..7 -> R.string.uv_index_high_f
        in 8..10 -> R.string.uv_index_very_high_f
        else -> {
            if (index > 11) R.string.uv_index_extreme_f
            else error("invalid uv index <$index>")
        }
    }
}