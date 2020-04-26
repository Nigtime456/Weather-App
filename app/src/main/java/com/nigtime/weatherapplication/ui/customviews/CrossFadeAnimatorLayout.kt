/*
 * Сreated by Igor Pokrovsky. 2020/4/24
 */

package com.nigtime.weatherapplication.ui.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionListenerAdapter
import com.nigtime.weatherapplication.R

/**
 * Layout, который показывает только один дочерний вью и анимирует
 * переход на другие вью в стиле cross fade анимации.
 */
class CrossFadeAnimatorLayout : FrameLayout {

    private companion object {
        const val NO_INDEX = -1
        const val DEFAULT_DELAY = 250
        const val DEFAULT_DURATION = 250
    }

    private var minStartDelay = DEFAULT_DELAY.toLong()
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

        currentDisplayedChild = typedArray
            .getInteger(R.styleable.CrossFadeAnimatorLayout_crossDisplayedChild, 0)
        minStartDelay = typedArray
            .getInteger(R.styleable.CrossFadeAnimatorLayout_crossStartDelay, DEFAULT_DELAY)
            .toLong()
        animationDuration = typedArray
            .getInteger(
                R.styleable.CrossFadeAnimatorLayout_crossAnimationDuration,
                DEFAULT_DURATION
            )
            .toLong()

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

    /**
     * Переключиться на другое вью.
     *
     * @param index - индекс другого элемента.
     */
    fun switchToChild(index: Int) {
        delaySwitch(index)
    }

    private fun delaySwitch(newIndex: Int) {
        if (newIndex == pendingDisplayedChild) {
            return
        }

        if (newIndex == currentDisplayedChild) {
            return
        }

        removeCallbacks(delaySwitch)
        pendingDisplayedChild = newIndex
        postDelayed(delaySwitch, 0)
    }

    private val delaySwitch = Runnable {
        val transition = Fade().apply {
            duration = animationDuration
        }
        transition.addListener(object : TransitionListenerAdapter() {
            override fun onTransitionEnd(transition: Transition) {

            }
        })
        //TransitionManager.beginDelayedTransition(this, transition)
        getChildAt(currentDisplayedChild).visibility = View.GONE
        getChildAt(pendingDisplayedChild).visibility = View.VISIBLE
        currentDisplayedChild = pendingDisplayedChild
        pendingDisplayedChild = NO_INDEX
    }
}


