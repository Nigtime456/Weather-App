/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.github.nigtime456.weather.utils.ui

import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorInt
import java.util.*

/**
 * Вспомогательный класс для выделения части текста, с помощью SpannableString
 *
 */
object ColorSpanHelper {

    /**
     * окрасить текст. Не учитывая регистр.
     *
     * @param source - исходная строка
     * @param substring - подстрока, которую нужно выделить
     * @return строка с добавленным спаном.
     */
    fun highlightText(source: String, substring: String, @ColorInt color: Int): SpannableString {
        val sourceStr = source.toLowerCase(Locale.getDefault())
        val substringStr = substring.toLowerCase(Locale.getDefault())

        var startIndex = sourceStr.indexOf(substringStr)
        var endIndex = startIndex + substring.length

        if (startIndex == -1)
            startIndex = 0
        if (endIndex == -1)
            endIndex = 0

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