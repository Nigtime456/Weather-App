/*
 * Сreated by Igor Pokrovsky. 2020/5/16
 */

package com.gmail.nigtime456.weatherapplication.storage.preference

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.preference.PreferenceManager
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.domain.repository.SettingsManager
import com.gmail.nigtime456.weatherapplication.domain.settings.*
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.*
import javax.inject.Inject


class SettingsManagerImpl @Inject constructor(context: Context) : SettingsManager,
    SharedPreferences.OnSharedPreferenceChangeListener {

    private companion object {
        const val KEY_UNIT_OF_TEMP = "unit_of_temp"
        const val KEY_UNIT_OF_LENGTH = "unit_of_length"
        const val KEY_UNIT_OF_SPEED = "unit_of_speed"
        const val KEY_UNIT_OF_PRESSURE = "unit_of_pressure"
        const val KEY_LANG = "lang"
        const val KEY_THEME = "theme"
        const val KEY_DAYS_COUNT = "internal_days_count"
    }

    private val preferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    private val langChangesSubject: Subject<Unit> = PublishSubject.create()
    private val themeChangesSubject: Subject<Unit> = PublishSubject.create()
    private val daysCountSubject: Subject<DaysCount> = BehaviorSubject.create()

    private lateinit var latestUnitOfTemp: UnitOfTemp
    private lateinit var latestUnitOfSpeed: UnitOfSpeed
    private lateinit var latestUnitOfLength: UnitOfLength
    private lateinit var latestUnitOfPressure: UnitOfPressure
    private lateinit var latestDaysCount: DaysCount

    init {
        initUnitOfTemp()
        initUnitOfSpeed()
        initUnitOfLength()
        initUnitOfPressure()
        initDaysCount()

        preferences.registerOnSharedPreferenceChangeListener(this)
    }

    private fun initUnitOfTemp() {
        val code = preferences.getStringOrThrow(KEY_UNIT_OF_TEMP)
        latestUnitOfTemp = UnitOfTemp.getByCode(code)
    }

    private fun initUnitOfSpeed() {
        val code = preferences.getStringOrThrow(KEY_UNIT_OF_SPEED)
        latestUnitOfSpeed = UnitOfSpeed.getByCode(code)
    }

    private fun initUnitOfLength() {
        val code = preferences.getStringOrThrow(KEY_UNIT_OF_LENGTH)
        latestUnitOfLength = UnitOfLength.getByCode(code)
    }

    private fun initUnitOfPressure() {
        val code = preferences.getStringOrThrow(KEY_UNIT_OF_PRESSURE)
        latestUnitOfPressure = UnitOfPressure.getByCode(code)
    }

    private fun initDaysCount() {
        val code = preferences.getString(KEY_DAYS_COUNT, DaysCount.getDefaultKey())
            ?: DaysCount.getDefaultKey()
        latestDaysCount = DaysCount.getByCode(code)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        when (key) {
            KEY_UNIT_OF_TEMP -> {
                initUnitOfTemp()
            }
            KEY_UNIT_OF_SPEED -> {
                initUnitOfSpeed()
            }
            KEY_UNIT_OF_LENGTH -> {
                initUnitOfLength()
            }
            KEY_UNIT_OF_PRESSURE -> {
                initUnitOfPressure()
            }
            KEY_LANG -> {
                langChangesSubject.onNext(Unit)
            }
            KEY_THEME -> {
                themeChangesSubject.onNext(Unit)
            }
        }
    }

    override fun getTheme(): Int {
        return when (val theme = preferences.getStringOrThrow(KEY_THEME)) {
            "dark" -> R.style.BaseTheme_Dark
            "light" -> R.style.BaseTheme_Light
            else -> error("unknown theme tag = $theme?")
        }
    }

    override fun getLocale(): Locale {
        val langCode = preferences.getStringOrThrow(KEY_LANG)
        return Locale(langCode)
    }

    override fun getDaysCount(): DaysCount = latestDaysCount

    override fun setDaysCount(daysCount: DaysCount) {
        Log.d("sas", "SETTINGS_MANAGER cur = [$latestDaysCount] new = [$daysCount]")
        if (latestDaysCount == daysCount) {
            return
        }
        latestDaysCount = daysCount
        daysCountSubject.onNext(latestDaysCount)
        saveDaysCount()
    }

    private fun saveDaysCount() {
        preferences.edit()
            .putString(KEY_DAYS_COUNT, latestDaysCount.getCode())
            .apply()
    }

    override fun getUnitOfTemp(): UnitOfTemp = latestUnitOfTemp

    override fun getUnitOfPressure(): UnitOfPressure = latestUnitOfPressure

    override fun getUnitOfSpeed(): UnitOfSpeed = latestUnitOfSpeed

    override fun getUnitOfLength(): UnitOfLength = latestUnitOfLength

    override fun observeLangChanges(): Observable<Unit> = langChangesSubject

    override fun observeThemeChanges(): Observable<Unit> = themeChangesSubject

    override fun observeDaysCountChanges(): Observable<DaysCount> = daysCountSubject

    private fun SharedPreferences.getStringOrThrow(key: String): String {
        return getString(key, null) ?: error("failed to get preference for key $key")
    }
}