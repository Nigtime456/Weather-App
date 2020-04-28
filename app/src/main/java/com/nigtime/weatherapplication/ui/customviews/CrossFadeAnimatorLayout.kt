/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.ui.customviews

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.get
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.nigtime.weatherapplication.R

class CrossFadeAnimatorLayout : FrameLayout {

    private companion object {
        const val NO_INDEX = -1
        const val DEFAULT_DELAY = 500
        const val DEFAULT_DURATION = 500
    }

    private var startDelayDuration = DEFAULT_DELAY.toLong()
    private var animationDuration = DEFAULT_DURATION.toLong()

    private var currentDisplayedChild = 0
    private var pendingDisplayedChild = NO_INDEX

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }


    private fun init(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CrossFadeAnimatorLayout)

        currentDisplayedChild =
            typedArray.getInteger(R.styleable.CrossFadeAnimatorLayout_crossDisplayedChild, 0)
        startDelayDuration =
            typedArray.getInteger(
                R.styleable.CrossFadeAnimatorLayout_crossStartDelay,
                DEFAULT_DELAY
            )
                .toLong()

        animationDuration = typedArray.getInteger(
            R.styleable.CrossFadeAnimatorLayout_crossAnimationDuration,
            DEFAULT_DURATION
        ).toLong()

        typedArray.recycle()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        //скрыть всё, кроме первоначального заданного
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.visibility = if (i == currentDisplayedChild) View.VISIBLE else View.INVISIBLE
        }
    }


    fun switchTo(childIndex: Int, immediately: Boolean) {
        //уже ожидает переключения
        if (childIndex == pendingDisplayedChild) {
            Log.d("sas", "already pending!")
            return
        }

        removeCallbacks(pendingSwitch)
        pendingDisplayedChild = NO_INDEX
        //уже отображается
        if (childIndex == currentDisplayedChild) {
            Log.d("sas", "same index!")
            return
        }

        pendingDisplayedChild = childIndex

        if (immediately) {
            Log.d("sas", "immediately")
            switch()
        } else {
            Log.d("sas", "delay = $startDelayDuration")
            postDelayed(pendingSwitch, startDelayDuration)
        }
    }

    private fun switch() {
        Log.d("sas", "switch to = $pendingDisplayedChild, duration = $animationDuration")
        val currentChild = getChildAt(currentDisplayedChild)
        val nextChild = get(pendingDisplayedChild)
        val transition = Fade().apply { duration = animationDuration }
        TransitionManager.beginDelayedTransition(this, transition)
        currentChild.visibility = View.GONE
        nextChild.visibility = View.VISIBLE
        currentDisplayedChild = pendingDisplayedChild
        pendingDisplayedChild = NO_INDEX
    }

    private val pendingSwitch = Runnable {
        Log.d("sas", "runnable")
        switch()
    }
}


