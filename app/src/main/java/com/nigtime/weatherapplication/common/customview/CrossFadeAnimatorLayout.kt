/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.common.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.nigtime.weatherapplication.R

/**
 * Layout который выполняет CrossFade анимацию,
 * принцип работы схожий с ContentLoadingProgressBar, но работает как ViewAnimator.
 * При переключение на другое View, Layout некоторое время
 * ототображает текущий вью, если за это время не было переключения на следующий
 * View то выполняется анимация, таким образом избегется резкое переключение
 * виджетов.
 *
 */
class CrossFadeAnimatorLayout : FrameLayout {

    private companion object {
        const val NO_INDEX = -1
        const val DEFAULT_DELAY = 5050
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
            child.visibility = if (i == currentDisplayedChild) View.VISIBLE else View.GONE
        }
    }


    /**
     * Переключиться на следующуй дочерений вью.
     * @param childIndex - индекс вью.
     * @param immediately - выполнить переключенеи немедленно, без ожидания
     */
    fun switchTo(childIndex: Int, immediately: Boolean) {

        //уже ожидает переключения
        if (childIndex == pendingDisplayedChild) {
            return
        }


        removePendingSwitch()

        //уже отображается
        if (childIndex == currentDisplayedChild) {
            return
        }

        pendingDisplayedChild = childIndex

        if (immediately) {
            switch()
        } else {
            postDelayed(pendingSwitch, startDelayDuration)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        removePendingSwitch()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removePendingSwitch()
    }

    private fun removePendingSwitch() {
        removeCallbacks(pendingSwitch)
        pendingDisplayedChild = NO_INDEX
    }




    private fun switch() {

        //TODO костыль
        /**
         * Если в CurrentForecast перейти на несколько страниц, потом
         * открыть SearchCity, то по нажатию кнопки назад, здесь ловим
         * pendingDisplayedChild == -1
         * Почему то коллбэки выполняются сразу
         */
        if (pendingDisplayedChild == NO_INDEX)
            return

        val currentChild = getChildAt(currentDisplayedChild)
        val nextChild = getChildAt(pendingDisplayedChild)


        val transition = Fade().apply { duration = animationDuration }
        TransitionManager.beginDelayedTransition(this, transition)

        currentChild.visibility = View.GONE
        nextChild.visibility = View.VISIBLE

        currentDisplayedChild = pendingDisplayedChild
        pendingDisplayedChild = NO_INDEX
    }

    private val pendingSwitch = Runnable { switch() }
}


