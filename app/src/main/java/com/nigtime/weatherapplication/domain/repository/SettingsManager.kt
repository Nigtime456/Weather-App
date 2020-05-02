/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.domain.repository

import com.nigtime.weatherapplication.domain.settings.TempUnit

interface SettingsManager {
    fun getTempUnit(): TempUnit
}