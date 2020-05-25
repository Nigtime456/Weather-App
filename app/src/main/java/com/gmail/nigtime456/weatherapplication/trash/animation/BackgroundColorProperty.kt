/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.gmail.nigtime456.weatherapplication.trash.animation

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Property
import android.view.View

//TODO useless
class BackgroundColorProperty :
    Property<View, Int>(Int::class.java, PROPERTY_NAME) {
    companion object {
        const val PROPERTY_NAME = "backgroundColor"
    }


    override fun set(view: View, value: Int) {
        view.setBackgroundColor(value)
    }

    override fun get(view: View): Int {
        return if (view.background is ColorDrawable) {
            (view.background as ColorDrawable).color
        } else {
            Color.TRANSPARENT
        }
    }
}