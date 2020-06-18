/*
 * Сreated by Igor Pokrovsky. 2020/6/15
 */

package com.github.nigtime456.weather.utils.ui

import android.content.Context
import android.text.format.DateUtils
import com.github.nigtime456.weather.R
import java.util.*
import java.util.concurrent.TimeUnit

object RelativeTimeUtils {

    fun formatTime(context: Context, timeMs: Long): CharSequence {
        val now = System.currentTimeMillis()
        val offset = now - timeMs
        return if (offset > TimeUnit.MINUTES.toMillis(1)) {
            context.getString(
                R.string.update_about_ago,
                getRelativeTime(timeMs, now)
            )
        } else {
            context.getString(R.string.update_less_than_minute_ago)
        }
    }

    private fun getRelativeTime(time: Long, now: Long): String {
        return DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS).toString()
            .toLowerCase(Locale.getDefault())
    }
}