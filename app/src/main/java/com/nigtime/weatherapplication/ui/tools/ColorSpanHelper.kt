/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.ui.tools

import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log

/**
 * Вспомогательный класс для выделения части текста, с помощью SpannableString
 *
 * @param color - цвет, в которой окрашивать текст
 */
class ColorSpanHelper constructor(private val color: Int) {

    /**
     * окрасить текст.
     * Не учитывая регистр.
     *
     * @param source - исходная строка
     * @param substring - подстрока, которую нужно выделить
     * @return строка с добавленным спаном.
     */
    fun highlightText(source: String, substring: String): SpannableString {

        var startIndex = source.toLowerCase().indexOf(substring.toLowerCase())
        var endIndex = startIndex + substring.length

        Log.d("sas", "source = $source , substring = $substring")

        if (startIndex == -1)
            startIndex = 0
        if (endIndex == -1)
            endIndex = 0


        Log.d("sas", "s = $startIndex , e = $endIndex")

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