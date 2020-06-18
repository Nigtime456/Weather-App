/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.github.nigtime456.weather.utils.ui

import android.text.InputFilter
import android.view.View
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(@StringRes message: Int, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

fun View.showSnackBar(@StringRes message: Int, @StringRes actionText: Int, action: () -> Unit) {
    Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
        .setAction(actionText) { action() }
        .show()
}

fun View.showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

fun SearchView.setMaxLength(length: Int) {
    val editText = findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
    editText.filters = arrayOf(InputFilter.LengthFilter(length))
}


