/*
 * Сreated by Igor Pokrovsky. 2020/5/6
 */

package com.nigtime.weatherapplication.domain.settings

import io.reactivex.Observable

/**
 * Класс предоставляющий информацию об настройках
 * (единиц измерений, локали, темы).
 *
 * Так же предоставляет методы для отслеживания изменений настроек.
 */
interface SettingsProvider {
    fun getLangCode(): String
    fun getUnitOfTemp(): UnitOfTemp
    fun getUnitOfPressure(): UnitOfPressure
    fun getUnitOfSpeed(): UnitOfSpeed
    fun getUnitOfLength(): UnitOfLength
    fun observeUnitsChanges(): Observable<Unit>
    fun observeLangChanges(): Observable<Unit>
    fun observeThemeChanges(): Observable<Unit>
}