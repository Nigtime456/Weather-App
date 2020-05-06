/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.common.testing

import android.content.Context
import com.nigtime.weatherapplication.domain.settings.SettingsManager
import com.nigtime.weatherapplication.domain.settings.TempUnit
import com.nigtime.weatherapplication.domain.settings.UnitFormatter

class FakeSettingsManager constructor(private val context: Context) :
    SettingsManager {

    override fun getUnitFormatter(): UnitFormatter {
        return UnitFormatter(context, TempUnit.Celsius)
    }
}