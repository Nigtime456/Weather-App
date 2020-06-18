/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/6
 */

package com.github.nigtime456.weather.data.repository

import com.github.nigtime456.weather.data.settings.UnitOfLength
import com.github.nigtime456.weather.data.settings.UnitOfPressure
import com.github.nigtime456.weather.data.settings.UnitOfSpeed
import com.github.nigtime456.weather.data.settings.UnitOfTemp

/**
 * Класс предоставляющий информацию об настройках
 * (единиц измерений, локали, темы).
 *
 * Так же предоставляет методы для отслеживания изменений настроек.
 */
interface SettingsProvider {

    fun getUnitOfTemp(): UnitOfTemp
    fun getUnitOfPressure(): UnitOfPressure
    fun getUnitOfSpeed(): UnitOfSpeed
    fun getUnitOfLength(): UnitOfLength
}