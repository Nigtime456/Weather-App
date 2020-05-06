/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.ui.helper

import android.content.Context
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.domain.repository.SettingsManager

class UnitFormatter(
    private val context: Context,
    private val settingsManager: SettingsManager
) {
    private val tempUnit = settingsManager.getTempUnit()


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
