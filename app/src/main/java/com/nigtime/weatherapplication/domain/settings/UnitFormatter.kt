/*
 * Сreated by Igor Pokrovsky. 2020/5/6
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.domain.settings

import android.content.Context
import com.nigtime.weatherapplication.R

class UnitFormatter(
    private val context: Context,
    private val tempUnit: TempUnit
) {

    fun formatTemp(temp: Double): String {
        return getString(R.string.units_temp_f).format(tempUnit.convertJsonValue(temp))
    }

    fun formatFeelsLikeTemp(temp: Double): String{
        return getString(R.string.units_feels_like_temp_f).format(tempUnit.convertJsonValue(temp))
    }


    private fun getString(id: Int): String {
        return context.getString(id)
    }
}
