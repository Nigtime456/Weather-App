/*
 * Сreated by Igor Pokrovsky. 2020/5/8
 */

package com.nigtime.weatherapplication.common.helper

import android.text.Editable
import android.text.TextWatcher

/**
 * Реализация [TextWatcher] которая позволяет переопределить только
 * нужный/-е методы.
 */
abstract class SimpleTextWatcher : TextWatcher {
    override fun afterTextChanged(s: Editable) {

    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

    }
}