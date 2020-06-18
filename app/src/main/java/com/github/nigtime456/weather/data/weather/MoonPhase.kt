/*
 * Сreated by Igor Pokrovsky. 2020/6/17
 */

package com.github.nigtime456.weather.data.weather

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.github.nigtime456.weather.R

enum class MoonPhase {
    NEW, WAXING_CRESCENT, FIRST_QUARTER, WAXING_GIBBOUS,
    FULL, WANING_GIBBOUS, LAST_QUARTER, WANING_CRESCENT;

    @DrawableRes
    fun getIco(): Int {
        return when (this) {
            NEW -> R.drawable.ic_moon_phase_new
            WAXING_CRESCENT -> R.drawable.ic_moon_phase_waxing_crescent
            FIRST_QUARTER -> R.drawable.ic_moon_phase_last_first_quarter
            WAXING_GIBBOUS -> R.drawable.ic_moon_phase_waxing_gibbous
            FULL -> R.drawable.ic_moon_phase_full
            WANING_GIBBOUS -> R.drawable.ic_moon_phase_waning_gibbous
            LAST_QUARTER -> R.drawable.ic_moon_phase_last_first_quarter
            WANING_CRESCENT -> R.drawable.ic_moon_phase_waning_crescent
        }
    }

    @StringRes
    fun getDescription(): Int {
        return when (this) {
            NEW -> R.string.moon_new
            WAXING_CRESCENT -> R.string.moon_waxing_crescent
            FIRST_QUARTER -> R.string.moon_first_quarter
            WAXING_GIBBOUS -> R.string.moon_waxing_gibbous
            FULL -> R.string.moon_full
            WANING_GIBBOUS -> R.string.moon_waning_gibbous
            LAST_QUARTER -> R.string.moon_last_quarter
            WANING_CRESCENT -> R.string.moon_waning_crescent
        }
    }

    companion object {

        fun fromMoonFraction(fraction: Double): MoonPhase {
            return when (fraction) {
                in 0.0..0.125 -> {
                    NEW
                }

                in 0.125..0.25 -> {
                    WAXING_CRESCENT
                }

                in 0.25..0.375 -> {
                    FIRST_QUARTER
                }

                in 0.375..0.5 -> {
                    WAXING_GIBBOUS
                }

                in 0.5..0.625 -> {
                    FULL
                }

                in 0.625..0.75 -> {
                    WANING_GIBBOUS
                }

                in 0.75..0.875 -> {
                    LAST_QUARTER
                }

                in 0.875..1.0 -> {
                    WANING_CRESCENT
                }

                else -> {
                    error("moon fraction must be in the range 0..1")
                }
            }
        }
    }
}