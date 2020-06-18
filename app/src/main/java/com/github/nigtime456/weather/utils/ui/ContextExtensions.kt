/*
 * Сreated by Igor Pokrovsky. 2020/6/5 
 */

package com.github.nigtime456.weather.utils.ui

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.DimenRes
import androidx.annotation.IntegerRes

@ColorInt
fun Context.getColorFromAttr(@AttrRes attr: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attr, typedValue, true)
    return typedValue.data
}

fun Context.getDimension(@DimenRes id: Int): Int {
    return resources.getDimensionPixelSize(id)
}

fun Context.getInteger(@IntegerRes id: Int): Int {
    return resources.getInteger(id)
}