/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.ui.customviews

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * Позволяет менять цвет через XML разметку Animator'ov
 */
class ColoredBackgroundFrameLayout : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private var backgroundColor = Color.TRANSPARENT

    override fun setBackgroundColor(color: Int) {
        super.setBackgroundColor(color)
        backgroundColor = color
    }

    fun getBackgroundColor(): Int = backgroundColor
}