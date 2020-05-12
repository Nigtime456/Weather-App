/*
 * Сreated by Igor Pokrovsky. 2020/5/11
 */

package com.nigtime.weatherapplication.net.mappers

import android.annotation.SuppressLint
import com.nigtime.weatherapplication.domain.forecast.SunInfo
import com.nigtime.weatherapplication.net.json.JsonCurrentData
import java.text.SimpleDateFormat
import java.util.*

class SunInfoMapper {
    companion object {
        private const val SERVER_TIME_ZONE = "GMT+0"

        @SuppressLint("SimpleDateFormat")
        private val TIME_FORMATTER = SimpleDateFormat("HH:mm")
    }

    fun map(jsonCurrentData: JsonCurrentData): SunInfo {
        return SunInfo("T", "T")
    }
}