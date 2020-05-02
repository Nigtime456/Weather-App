/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.utility.testing

import com.nigtime.weatherapplication.domain.repository.SettingsManager
import com.nigtime.weatherapplication.domain.settings.TempUnit

class FakeSettingManager : SettingsManager {
    override fun getTempUnit(): TempUnit {
        return TempUnit.Celsius
    }
}