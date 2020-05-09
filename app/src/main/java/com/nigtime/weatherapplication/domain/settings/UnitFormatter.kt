/*
 * Сreated by Igor Pokrovsky. 2020/5/6
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.domain.settings

import android.content.Context
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.domain.forecast.DailyForecast
import java.text.SimpleDateFormat
import java.util.*

class UnitFormatter(
    private val context: Context,
    private val unitOfTemp: UnitOfTemp,
    private val unitOfSpeed: UnitOfSpeed,
    private val unitOfPressure: UnitOfPressure
) {
    private val weekDayFormatter = SimpleDateFormat("EEEE", Locale.getDefault())
    private val dayOfMonthFormatter = SimpleDateFormat("d MMMM (E)", Locale.getDefault())

    fun formatTemp(temp: Double): String {
        return getString(R.string.units_temp_f).format(unitOfTemp.convertJsonValue(temp))
    }

    fun formatFeelsLikeTemp(temp: Double): String {
        return getString(R.string.units_feels_like_temp_f).format(unitOfTemp.convertJsonValue(temp))
    }

    fun formatSpeed(speed: Double): String {
        return getString(unitOfSpeed.getFormattingPattern()).format(speed)
    }

    fun formatHumidity(humidity: Int): String {
        return getString(R.string.units_humidity_f).format(humidity)
    }

    fun formatPressure(pressure: Double): String {
        return getString(unitOfPressure.getFormattingPattern()).format(pressure)
    }

    fun getCompactDateByDailyWeather(dailyWeather: DailyForecast.DailyWeather): String {
        return when (dailyWeather.index) {
            0 -> getString(R.string.units_today)
            1 -> getString(R.string.units_tomorrow)
            in 2..4 -> weekDayFormatter.format(dailyWeather.unixTimestamp)
            else -> dayOfMonthFormatter.format(dailyWeather.unixTimestamp)
        }
    }

    private fun getString(id: Int): String {
        return context.getString(id)
    }
}
