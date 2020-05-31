/*
 * Сreated by Igor Pokrovsky. 2020/5/9
 */

package com.gmail.nigtime456.weatherapplication.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewTreeObserver
import androidx.core.widget.NestedScrollView

/**
 * NestedScrollView который откладывает установку скролла, если layout ещё не
 * сформирован
 */
class PendingNestedScrollView : NestedScrollView, ViewTreeObserver.OnGlobalLayoutListener {

    private var pendingScrollY = -1

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    fun delayScrollY(newScroll: Int) {
        if (this.scrollY == newScroll)
            return

        if (height == 0) {
            pendingScrollY = newScroll
            viewTreeObserver.addOnGlobalLayoutListener(this)
        } else {
            this.scrollY = newScroll
        }
    }

    override fun onGlobalLayout() {
        if (pendingScrollY != -1) {
            scrollY = pendingScrollY
            if (scrollY == pendingScrollY) {
                pendingScrollY = -1
                viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        }
    }
}