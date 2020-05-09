/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.common.testing

import android.content.Context
import com.nigtime.weatherapplication.domain.settings.*

class FakeSettingsManager constructor(private val context: Context) :
    SettingsManager {

    override fun getUnitFormatter(): UnitFormatter {
        return UnitFormatter(
            context,
            UnitOfTemp.Fahrenheit,
            UnitOfSpeed.KilometrePerHour,
            UnitOfPressure.MBar,
            UnitOfLength.Kilometre
        )
    }
}