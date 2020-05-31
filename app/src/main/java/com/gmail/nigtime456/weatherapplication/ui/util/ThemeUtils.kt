/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.gmail.nigtime456.weatherapplication.ui.util

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt

/**
 * Класс для получения аттрибутов с тем
 */
object ThemeUtils {

    @ColorInt
    fun getAttrColor(context: Context, @AttrRes attrColor: Int): Int {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(attrColor, typedValue, true)
        return typedValue.data
    }
}