/*
 * Сreated by Igor Pokrovsky. 2020/5/2
 */

package com.gmail.nigtime456.weatherapplication.trash

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar

//TODO useless
class CenteredTitleToolbar : Toolbar {
    private lateinit var titleTextView: AppCompatTextView

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        val childCount = childCount
        for (i in 0 until childCount) {
            val view = this.getChildAt(i)
            if (view is TextView) {
                forceTitleCenter(view, l, r)
            }
        }
    }


    /**
     * Centering the layout.
     *
     * @param view The view to be centered
     */
    private fun forceTitleCenter(view: TextView, l: Int, r: Int) {
        val top = view.top
        val bottom = view.bottom
        view.layout(l, top, r, bottom)
        navigationIcon?.let {
            view.setPadding(it.intrinsicWidth, 0, 0, 0)
        }
        view.gravity = Gravity.CENTER
    }
}