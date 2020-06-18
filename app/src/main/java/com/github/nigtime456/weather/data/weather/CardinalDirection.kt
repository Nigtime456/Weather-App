/*
 * Сreated by Igor Pokrovsky. 2020/6/17
 */

package com.github.nigtime456.weather.data.weather

import com.github.nigtime456.weather.R

enum class CardinalDirection {
    N, NNE, NE, ENE,
    E, ESE, SE, SSE,
    S, SSW, SW, WSW,
    W, WNW, NW, NNW;

    fun getAbbreviatedDirection(): Int {
        return when (this) {
            N -> R.string.wind_dir_n
            NNE -> R.string.wind_dir_nne
            NE -> R.string.wind_dir_ne
            ENE -> R.string.wind_dir_ene
            E -> R.string.wind_dir_e
            ESE -> R.string.wind_dir_ese
            SE -> R.string.wind_dir_se
            SSE -> R.string.wind_dir_sse
            S -> R.string.wind_dir_s
            SSW -> R.string.wind_dir_ssw
            SW -> R.string.wind_dir_sw
            WSW -> R.string.wind_dir_wsw
            W -> R.string.wind_dir_w
            WNW -> R.string.wind_dir_wnw
            NW -> R.string.wind_dir_nw
            NNW -> R.string.wind_dir_nnw
        }
    }

    /**
    directions:
    N   -> 348.75 - 11.25
    NNE -> 11.25  - 35.75
    NE  -> 35.75  - 56.25
    ENE -> 56.25  - 78.75
    E   -> 78.75  - 101.25
    ESE -> 101.25 - 123.75
    SE  -> 123.75 - 146.25
    SSE -> 146.25 - 168.75
    S   -> 168.75 - 191.25
    SSW -> 191.25 - 213.75
    SW  -> 213.75 - 236.25
    WSW -> 236.25 - 258.75
    W   -> 258.75 - 281.25
    WNW -> 281.25 - 303.75
    NW  -> 303.75 - 326.25
    NNW -> 326.25 - 348.75
     */
    companion object {
        fun fromDegrees(degrees: Int): CardinalDirection {
            ((100 + 6.25) / 12.5).toInt() % 8
            val num = ((degrees + 11.25) / 22.5).toInt() % 16
            return values()[num]
        }
    }
}