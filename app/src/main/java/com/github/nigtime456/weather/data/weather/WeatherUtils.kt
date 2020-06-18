/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

package com.github.nigtime456.weather.data.weather

import android.content.Context
import androidx.annotation.DrawableRes
import com.github.nigtime456.weather.R
import com.github.nigtime456.weather.data.settings.UnitOfLength
import com.github.nigtime456.weather.data.settings.UnitOfPressure
import com.github.nigtime456.weather.data.settings.UnitOfSpeed
import com.github.nigtime456.weather.data.settings.UnitOfTemp

object WeatherUtils {

    fun formatTemp(context: Context, unitOfTemp: UnitOfTemp, temp: Double): String {
        return context.getString(R.string.units_temp_f, unitOfTemp.convert(temp))
    }

    fun formatApparentTemp(context: Context, unitOfTemp: UnitOfTemp, temp: Double): String {
        return context.getString(R.string.units_apparent_temp_f, unitOfTemp.convert(temp))
    }

    fun formatLength(context: Context, unitOfLength: UnitOfLength, length: Double): String {
        return context.getString(unitOfLength.getFormattingPattern(), unitOfLength.convert(length))
    }

    fun formatPressure(context: Context, unitOfPressure: UnitOfPressure, pressure: Double): String {
        return context.getString(
            unitOfPressure.getFormattingPattern(),
            unitOfPressure.convert(pressure)
        )
    }

    fun formatSpeed(context: Context, unitOfSpeed: UnitOfSpeed, speed: Double): String {
        return context.getString(unitOfSpeed.getFormattingPattern(), unitOfSpeed.convert(speed))
    }

    fun formatCloudsCoverage(context: Context, clouds: Double): String {
        return context.getString(R.string.units_percent_pattern_f, clouds * 100)
    }

    fun formatHumidity(context: Context, humidity: Double): String {
        return context.getString(R.string.units_percent_pattern_f, humidity * 100)
    }

    fun getUvIndexDescription(context: Context, uvIndex: Int): String {
        val descriptionResId = UvIndex.fromIndex(uvIndex).getDescription()
        return context.getString(descriptionResId)
    }

    fun getWeatherDescription(context: Context, code: String): String {
        val descriptionResId = WeatherCondition.fromCode(code).getDescription()
        return context.getString(descriptionResId)
    }

    @DrawableRes
    fun getWeatherIcon(code: String): Int {
        return WeatherCondition.fromCode(code).getIco()
    }

    @DrawableRes
    fun getMoonIco(moonPhase: Double): Int {
        return MoonPhase.fromMoonFraction(moonPhase).getIco()
    }

    fun getMoonDescription(context: Context, moonPhase: Double): String {
        val descriptionResId = MoonPhase.fromMoonFraction(moonPhase).getDescription()
        return context.getString(descriptionResId)
    }

    fun getAbbreviatedDirection(context: Context, degrees: Int): String {
        val directionResId = CardinalDirection.fromDegrees(degrees).getAbbreviatedDirection()
        return context.getString(directionResId)
    }
}