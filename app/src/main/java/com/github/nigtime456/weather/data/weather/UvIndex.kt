/*
 * Сreated by Igor Pokrovsky. 2020/6/17
 */

package com.github.nigtime456.weather.data.weather

import androidx.annotation.StringRes
import com.github.nigtime456.weather.R

enum class UvIndex {
    LOW, MODERATE, HIGH, VERY_HIGH, EXTREME;

    @StringRes
    fun getDescription(): Int {
        return when (this) {
            LOW -> R.string.uv_index_low
            MODERATE -> R.string.uv_index_moderate
            HIGH -> R.string.uv_index_high
            VERY_HIGH -> R.string.uv_index_very_high
            EXTREME -> R.string.uv_index_extreme
        }
    }

    companion object {
        fun fromIndex(index: Int): UvIndex {
            return when (index) {
                in 0..2 -> LOW
                in 3..5 -> MODERATE
                in 6..7 -> HIGH
                in 8..10 -> VERY_HIGH
                else -> EXTREME
            }
        }
    }
}