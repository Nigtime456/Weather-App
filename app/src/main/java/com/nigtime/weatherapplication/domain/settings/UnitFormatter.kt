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
    private val tempUnit: TempUnit
) {
    private val weekDayFormatter = SimpleDateFormat("EEEE", Locale.getDefault())
    private val dayOfMonthFormatter = SimpleDateFormat("d MMMM (E)", Locale.getDefault())

    fun formatTemp(temp: Double): String {
        return getString(R.string.units_temp_f).format(tempUnit.convertJsonValue(temp))
    }

    fun formatFeelsLikeTemp(temp: Double): String {
        return getString(R.string.units_feels_like_temp_f).format(tempUnit.convertJsonValue(temp))
    }

    fun getCompactDateByDailyWeather(dailyWeather: DailyForecast.DailyWeather): String {
        return when (dailyWeather.index) {
            0 -> "today"
            1 -> "tommorow"
            in 2..4 -> weekDayFormatter.format(dailyWeather.unixTime)
            else -> dayOfMonthFormatter.format(dailyWeather.unixTime)
        }
    }

    private fun getString(id: Int): String {
        return context.getString(id)
    }
}
