/*
 * Сreated by Igor Pokrovsky. 2020/5/6
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/6
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.domain.settings

import io.reactivex.Observable

interface SettingsManager {
    fun getUnitOfTemp(): UnitOfTemp
    fun getUnitOfPressure(): UnitOfPressure
    fun getUnitOfSpeed(): UnitOfSpeed
    fun getUnitOfLength(): UnitOfLength
    fun observeUnitsChanges(): Observable<Unit>
    fun observeLangChanges(): Observable<Unit>
    fun observeThemeChanges(): Observable<Unit>
}