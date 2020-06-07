/*
 * Сreated by Igor Pokrovsky. 2020/6/2
 */

package com.gmail.nigtime456.weatherapplication.customview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.cardview.widget.CardView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView

/**
 * [CardView], которая подписывается на [RecyclerView] или [NestedScrollView] и в зависимости от,
 * скролла изменяют высоту, реализую шаблон "lift on scroll"
 */
class LiftingCardView : CardView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    fun setLift(lift: Boolean) {
        if (isActivated == lift)
            return
        cardElevation
        Log.d("sas", "lift")
        isActivated = lift
    }

    fun setTarget(view: View) {
        if (view is RecyclerView) {
            RecyclerObserver(view)
        } else if (view is NestedScrollView) {
            NestedScrollListener(view)
        }
    }

    inner class RecyclerObserver(private var recyclerView: RecyclerView) :
        ViewTreeObserver.OnScrollChangedListener {

        init {
            recyclerView.viewTreeObserver.addOnScrollChangedListener(this)
        }

        override fun onScrollChanged() {
            val scrollOffset = recyclerView.computeVerticalScrollOffset()
            setLift(scrollOffset > 0)
        }
    }

    inner class NestedScrollListener(nestedScrollView: NestedScrollView) :
        NestedScrollView.OnScrollChangeListener {

        init {
            nestedScrollView.setOnScrollChangeListener(this)
        }

        override fun onScrollChange(
            v: NestedScrollView?,
            scrollX: Int,
            scrollY: Int,
            oldScrollX: Int,
            oldScrollY: Int
        ) {
            setLift(scrollY > 0)
        }

    }
}