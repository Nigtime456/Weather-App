/*
 * Сreated by Igor Pokrovsky. 2020/5/16
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/16
 */

package com.nigtime.weatherapplication.storage.preference

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.preference.PreferenceManager
import com.nigtime.weatherapplication.domain.settings.*
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class SettingsManagerImpl constructor(private val context: Context) : SettingsManager,
    SharedPreferences.OnSharedPreferenceChangeListener {


    private companion object {
        const val KEY_TEMP_UNIT = "temp_unit"
        const val KEY_LENGTH_UNIT = "length_unit"
        const val KEY_SPEED_UNIT = "speed_unit"
    }

    private val unitSubject: Subject<UnitFormatter> = BehaviorSubject.create()
    private val sharedPreferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    private lateinit var latestTempUnit: UnitOfTemp
    private lateinit var latestSpeedUnit: UnitOfSpeed
    private lateinit var latestLengthUnit: UnitOfLength
    private var latestPressure: UnitOfPressure = UnitOfPressure.default()

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        makeNewUnitFormatter()
    }

    override fun getUnitFormatter(): Observable<UnitFormatter> = unitSubject

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            KEY_LENGTH_UNIT, KEY_SPEED_UNIT, KEY_TEMP_UNIT -> {
                updateUnitFormatter(key)
            }
        }
    }


    private fun updateUnitFormatter(key: String) {
        val start = System.currentTimeMillis()
        when (key) {
            KEY_TEMP_UNIT -> {
                val temp = sharedPreferences.getStringOrThrow(KEY_TEMP_UNIT)
                latestTempUnit = UnitOfTemp.getByCode(temp)
            }
            KEY_SPEED_UNIT -> {
                val speed = sharedPreferences.getStringOrThrow(KEY_SPEED_UNIT)
                latestSpeedUnit = UnitOfSpeed.getByCode(speed)
            }
            KEY_LENGTH_UNIT -> {
                val length = sharedPreferences.getStringOrThrow(KEY_LENGTH_UNIT)
                latestLengthUnit = UnitOfLength.getByCode(length)
            }
        }
        makeUnitFormatter()
        Log.d("sas", "update = ${System.currentTimeMillis() - start}")
    }


    private fun makeNewUnitFormatter() {
        val start = System.currentTimeMillis()
        val temp = sharedPreferences.getStringOrThrow(KEY_TEMP_UNIT)
        val speed = sharedPreferences.getStringOrThrow(KEY_SPEED_UNIT)
        val length = sharedPreferences.getStringOrThrow(KEY_LENGTH_UNIT)

        latestTempUnit = UnitOfTemp.getByCode(temp)
        latestSpeedUnit = UnitOfSpeed.getByCode(speed)
        latestLengthUnit = UnitOfLength.getByCode(length)

        makeUnitFormatter()
        Log.d("sas", "create = ${System.currentTimeMillis() - start}")
    }

    private fun makeUnitFormatter() {
        val unitFormatter = UnitFormatter(
            context,
            latestTempUnit,
            latestSpeedUnit,
            latestPressure,
            latestLengthUnit
        )
        unitSubject.onNext(unitFormatter)
    }


    private fun SharedPreferences.getStringOrThrow(key: String): String {
        return getString(key, null) ?: error("failed to get preference for key $key")
    }
}