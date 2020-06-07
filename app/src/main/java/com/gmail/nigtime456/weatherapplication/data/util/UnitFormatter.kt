/*
 * Сreated by Igor Pokrovsky. 2020/5/6
 */

package com.gmail.nigtime456.weatherapplication.data.util

import android.content.Context
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.data.forecast.AirQuality
import com.gmail.nigtime456.weatherapplication.data.forecast.UvIndex
import com.gmail.nigtime456.weatherapplication.data.settings.UnitOfLength
import com.gmail.nigtime456.weatherapplication.data.settings.UnitOfPressure
import com.gmail.nigtime456.weatherapplication.data.settings.UnitOfSpeed
import com.gmail.nigtime456.weatherapplication.data.settings.UnitOfTemp

object UnitFormatter {

    fun formatTemp(context: Context, unitOfTemp: UnitOfTemp, temp: Double): String {
        return context.getString(R.string.units_temp_f, unitOfTemp.convert(temp))
    }

    fun formatFeelsLikeTemp(context: Context, unitOfTemp: UnitOfTemp, temp: Double): String {
        return context.getString(R.string.units_feels_like_temp_f, unitOfTemp.convert(temp))
    }

    fun formatSpeed(context: Context, unitOfSpeed: UnitOfSpeed, speed: Double): String {
        return context.getString(unitOfSpeed.getFormattingPattern(), unitOfSpeed.convert(speed))
    }

    fun formatHumidity(context: Context, humidity: Int): String {
        return context.getString(R.string.units_percent_pattern_f, humidity)
    }

    fun formatPressure(context: Context, unitOfPressure: UnitOfPressure, pressure: Double): String {
        return context.getString(
            unitOfPressure.getFormattingPattern(),
            unitOfPressure.convert(pressure)
        )
    }

    fun formatProbabilityOfPrecipitation(context: Context, precipitation: Int): String {
        return context.getString(R.string.units_percent_pattern_f, precipitation)
    }

    fun formatVisibility(context: Context, unitOfLength: UnitOfLength, visibility: Double): String {
        return context.getString(
            unitOfLength.getFormattingPattern(),
            unitOfLength.convert(visibility)
        )
    }

    fun formatAirQuality(context: Context, airQuality: AirQuality): String {
        return context.getString(airQuality.getFormattingString(), airQuality.index)
    }

    fun formatUvIndex(context: Context, uvIndex: UvIndex): String {
        return context.getString(uvIndex.getFormattingString(), uvIndex.index)
    }

    fun formatCloudsCoverage(context: Context, clouds: Int): String {
        return context.getString(R.string.units_percent_pattern_f, clouds)
    }
}
