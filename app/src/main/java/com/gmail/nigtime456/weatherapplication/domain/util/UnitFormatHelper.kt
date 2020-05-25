/*
 * Сreated by Igor Pokrovsky. 2020/5/6
 */

package com.gmail.nigtime456.weatherapplication.domain.util

import android.content.Context
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.domain.forecast.AirQuality
import com.gmail.nigtime456.weatherapplication.domain.forecast.UvIndex
import com.gmail.nigtime456.weatherapplication.domain.settings.UnitOfLength
import com.gmail.nigtime456.weatherapplication.domain.settings.UnitOfPressure
import com.gmail.nigtime456.weatherapplication.domain.settings.UnitOfSpeed
import com.gmail.nigtime456.weatherapplication.domain.settings.UnitOfTemp

class UnitFormatHelper constructor(private val context: Context) {

    fun formatTemp(unitOfTemp: UnitOfTemp, temp: Double): String {
        return context.getString(R.string.units_temp_f, unitOfTemp.convert(temp))
    }

    fun formatFeelsLikeTemp(unitOfTemp: UnitOfTemp, temp: Double): String {
        return context.getString(R.string.units_feels_like_temp_f, unitOfTemp.convert(temp))
    }

    fun formatSpeed(unitOfSpeed: UnitOfSpeed, speed: Double): String {
        return context.getString(unitOfSpeed.getFormattingPattern(), unitOfSpeed.convert(speed))
    }

    fun formatHumidity(humidity: Int): String {

        return context.getString(R.string.units_percent_pattern_f, humidity)
    }

    fun formatPressure(unitOfPressure: UnitOfPressure, pressure: Double): String {
        return context.getString(
            unitOfPressure.getFormattingPattern(),
            unitOfPressure.convert(pressure)
        )
    }

    fun formatProbabilityOfPrecipitation(precipitation: Int): String {
        return context.getString(R.string.units_percent_pattern_f, precipitation)
    }

    fun formatVisibility(unitOfLength: UnitOfLength, visibility: Double): String {
        return context.getString(
            unitOfLength.getFormattingPattern(),
            unitOfLength.convert(visibility)
        )
    }

    fun formatAirQuality(airQuality: AirQuality): String {
        return context.getString(airQuality.getFormattingString(), airQuality.index)
    }

    fun formatUvIndex(uvIndex: UvIndex): String {
        return context.getString(uvIndex.getFormattingString(), uvIndex.index)
    }

    fun formatCloudsCoverage(clouds: Int): String {
        return context.getString(R.string.units_percent_pattern_f, clouds)
    }
}
