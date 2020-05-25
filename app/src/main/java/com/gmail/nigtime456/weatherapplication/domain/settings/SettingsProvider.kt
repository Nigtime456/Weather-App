/*
 * Сreated by Igor Pokrovsky. 2020/5/6
 */

package com.gmail.nigtime456.weatherapplication.domain.settings

import androidx.annotation.StyleRes
import io.reactivex.Observable
import java.util.*

/**
 * Класс предоставляющий информацию об настройках
 * (единиц измерений, локали, темы).
 *
 * Так же предоставляет методы для отслеживания изменений настроек.
 */
interface SettingsProvider {
    @StyleRes
    fun getTheme(): Int
    fun getLocale(): Locale
    fun getUnitOfTemp(): UnitOfTemp
    fun getUnitOfPressure(): UnitOfPressure
    fun getUnitOfSpeed(): UnitOfSpeed
    fun getUnitOfLength(): UnitOfLength
    fun observeUnitsChanges(): Observable<Unit>
    fun observeLangChanges(): Observable<Unit>
    fun observeThemeChanges(): Observable<Unit>
}