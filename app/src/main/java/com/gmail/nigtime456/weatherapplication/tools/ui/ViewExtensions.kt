/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/29
 */

package com.gmail.nigtime456.weatherapplication.tools.ui

import android.view.View
import android.widget.EditText
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(@StringRes message: Int, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

fun View.showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

fun EditText.changeTextListener(onChanged: (String) -> Unit) {
    addTextChangedListener(object : SimpleTextWatcher() {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            onChanged(s.toString())
        }
    })
}
