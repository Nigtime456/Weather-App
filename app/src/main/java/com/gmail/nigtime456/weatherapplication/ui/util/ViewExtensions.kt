/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/29
 */

package com.gmail.nigtime456.weatherapplication.ui.util

import android.view.View
import android.widget.EditText
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

fun View.showSnackBar(@StringRes message: Int, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}
