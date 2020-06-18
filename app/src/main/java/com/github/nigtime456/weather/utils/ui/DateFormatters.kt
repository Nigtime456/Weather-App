/*
 * Сreated by Igor Pokrovsky. 2020/5/18
 */

package com.github.nigtime456.weather.utils.ui

import android.content.Context
import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateFormatters {
    private const val HOURS_PATTERN_24 = "HH:mm"
    private const val HOURS_PATTERN_12 = "KK:mm a"
    private const val WEEKDAY_PATTERN = "EEEE"
    private const val SHORT_WEEKDAY = "d"

    fun getHoursFormatter(context: Context, timeZone: String): SimpleDateFormat {
        return if (DateFormat.is24HourFormat(context)) {
            SimpleDateFormat(HOURS_PATTERN_24, Locale.getDefault())
        } else {
            SimpleDateFormat(HOURS_PATTERN_12, Locale.getDefault())
        }.also {
            it.timeZone = TimeZone.getTimeZone(timeZone)
        }
    }

    fun getWeekdayFormatter(): SimpleDateFormat {
        val pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), WEEKDAY_PATTERN)
        return SimpleDateFormat(pattern, Locale.getDefault())
    }

    fun getShortWeekdayWithDayFormatter(): SimpleDateFormat {
        return SimpleDateFormat(SHORT_WEEKDAY, Locale.getDefault())
    }
}