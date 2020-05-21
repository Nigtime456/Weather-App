/*
 * Сreated by Igor Pokrovsky. 2020/5/18
 */

package com.nigtime.weatherapplication.common.util

import android.content.Context
import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateFormatHolder {
    private const val HOURS_PATTERN_24 = "HH:mm"
    private const val HOURS_PATTERN_12 = "KK:mm a"
    private const val WEEKDAY_PATTERN = "EEEE"
    private const val DAY_OF_MONTH_PATTERN = "d MMMM (E)"

    fun getHoursFormatter(context: Context): SimpleDateFormat {
        return if (DateFormat.is24HourFormat(context)) {
            SimpleDateFormat(HOURS_PATTERN_24, Locale.getDefault())
        } else {
            SimpleDateFormat(HOURS_PATTERN_12, Locale.getDefault())
        }
    }

    fun getWeekdayFormatter(): SimpleDateFormat {
        val pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), WEEKDAY_PATTERN)
        return SimpleDateFormat(pattern, Locale.getDefault())
    }

    fun getDayOfMonthFormatter(): SimpleDateFormat {
        val pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), DAY_OF_MONTH_PATTERN)
        return SimpleDateFormat(pattern, Locale.getDefault())
    }
}