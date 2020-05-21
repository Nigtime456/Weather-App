/*
 * Сreated by Igor Pokrovsky. 2020/5/16
 */

package com.nigtime.weatherapplication.storage.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.nigtime.weatherapplication.domain.settings.*
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject


class SettingsManagerImpl constructor(context: Context) : SettingsManager,
    SharedPreferences.OnSharedPreferenceChangeListener {

    private companion object {
        const val KEY_UNIT_OF_TEMP = "unit_of_temp"
        const val KEY_UNIT_OF_LENGTH = "unit_of_length"
        const val KEY_UNIT_OF_SPEED = "unit_of_speed"
        const val KEY_UNIT_OF_PRESSURE = "unit_of_pressure"
        const val KEY_LANG = "lang"
        const val KEY_THEME = "theme"
    }

    private val sharedPreferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    private val unitsChangesSubject: Subject<Unit> = PublishSubject.create()
    private val langChangesSubject: Subject<Unit> = PublishSubject.create()
    private val themeChangesSubject: Subject<Unit> = PublishSubject.create()

    private lateinit var latestUnitOfTemp: UnitOfTemp
    private lateinit var latestUnitOfSpeed: UnitOfSpeed
    private lateinit var latestUnitOfLength: UnitOfLength
    private lateinit var latestUnitOfPressure: UnitOfPressure

    init {
        initUnitOfTemp()
        initUnitOfSpeed()
        initUnitOfLength()
        initUnitOfPressure()

        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    private fun initUnitOfTemp() {
        val code = sharedPreferences.getStringOrThrow(KEY_UNIT_OF_TEMP)
        latestUnitOfTemp = UnitOfTemp.getByCode(code)
    }

    private fun initUnitOfSpeed() {
        val code = sharedPreferences.getStringOrThrow(KEY_UNIT_OF_SPEED)
        latestUnitOfSpeed = UnitOfSpeed.getByCode(code)
    }

    private fun initUnitOfLength() {
        val code = sharedPreferences.getStringOrThrow(KEY_UNIT_OF_LENGTH)
        latestUnitOfLength = UnitOfLength.getByCode(code)
    }

    private fun initUnitOfPressure() {
        val code = sharedPreferences.getStringOrThrow(KEY_UNIT_OF_PRESSURE)
        latestUnitOfPressure = UnitOfPressure.getByCode(code)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        when (key) {
            KEY_UNIT_OF_TEMP -> {
                initUnitOfTemp()
                unitsChangesSubject.onNext(Unit)
            }
            KEY_UNIT_OF_SPEED -> {
                initUnitOfSpeed()
                unitsChangesSubject.onNext(Unit)
            }
            KEY_UNIT_OF_LENGTH -> {
                initUnitOfLength()
                unitsChangesSubject.onNext(Unit)
            }
            KEY_UNIT_OF_PRESSURE -> {
                initUnitOfPressure()
                unitsChangesSubject.onNext(Unit)
            }
            KEY_LANG -> {
                langChangesSubject.onNext(Unit)
            }
            KEY_THEME -> {
                themeChangesSubject.onNext(Unit)
            }
        }
    }

    override fun getUnitOfTemp(): UnitOfTemp = latestUnitOfTemp

    override fun getUnitOfPressure(): UnitOfPressure = latestUnitOfPressure

    override fun getUnitOfSpeed(): UnitOfSpeed = latestUnitOfSpeed

    override fun getUnitOfLength(): UnitOfLength = latestUnitOfLength

    override fun observeUnitsChanges(): Observable<Unit> = unitsChangesSubject

    override fun observeLangChanges(): Observable<Unit> = langChangesSubject

    override fun observeThemeChanges(): Observable<Unit> = themeChangesSubject

    private fun SharedPreferences.getStringOrThrow(key: String): String {
        return getString(key, null) ?: error("failed to get preference for key $key")
    }
}