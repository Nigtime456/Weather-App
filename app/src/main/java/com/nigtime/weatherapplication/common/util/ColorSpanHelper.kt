/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.common.util

import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import java.util.*

/**
 * Вспомогательный класс для выделения части текста, с помощью SpannableString
 *
 * @param color - цвет, в которой окрашивать текст
 */
class ColorSpanHelper constructor(private val color: Int) {

    /**
     * окрасить текст. Не учитывая регистр.
     *
     * @param source - исходная строка
     * @param substring - подстрока, которую нужно выделить
     * @return строка с добавленным спаном.
     */
    fun highlightText(source: String, substring: String): SpannableString {
        Log.d("sas", "source = $source , substring = $substring")

        val sourceStr = source.toLowerCase(Locale.getDefault())
        val substringStr = substring.toLowerCase(Locale.getDefault())

        var startIndex = sourceStr.indexOf(substringStr)
        var endIndex = startIndex + substring.length
        Log.d("sas", "B startIndex = $startIndex endIndex = $endIndex")
        if (startIndex == -1)
            startIndex = 0
        if (endIndex == -1)
            endIndex = 0

        Log.d("sas", "A startIndex = $startIndex endIndex = $endIndex")

        return if (startIndex == 0 && endIndex == 0) {
            SpannableString(source)
        } else {
            SpannableString(source).apply {
                setSpan(
                    ForegroundColorSpan(color),
                    startIndex,
                    endIndex,
                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
    }
}