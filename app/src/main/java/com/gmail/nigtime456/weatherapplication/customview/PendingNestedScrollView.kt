/*
 * Сreated by Igor Pokrovsky. 2020/5/9
 */

package com.gmail.nigtime456.weatherapplication.customview

import android.content.Context
import android.util.AttributeSet
import androidx.core.widget.NestedScrollView

/**
 * NestedScrollView который откладывает установку скролла, если layout ещё не
 * сформирован, тем самым скролл не будет "проглочен".
 */
class PendingNestedScrollView : NestedScrollView, NestedScrollView.OnScrollChangeListener {

    interface ScrollListener {
        fun onScroll(scrollX: Int, scrollY: Int)
    }

    private val scrollListeners = mutableListOf<ScrollListener>()
    private var pendingScrollY = -1
    private var hasLayout = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        setOnScrollChangeListener(this)
    }

    fun delayScrollY(scrollY: Int) {
        if (this.scrollY == scrollY)
            return

        if (hasLayout) {
            this.scrollY = scrollY
        } else {
            //layout ещё не сформирован, откладываем скроллл
            pendingScrollY = scrollY
        }
    }

    fun setScrollListener(scrollListener: ScrollListener) {
        scrollListeners.add(scrollListener)
    }

    fun removeScrollListener(scrollListener: ScrollListener) {
        scrollListeners.remove(scrollListener)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        hasLayout = true
        scrollY = pendingScrollY
        pendingScrollY = -1
    }

    override fun onScrollChange(
        v: NestedScrollView?,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {
        scrollListeners.forEach { scrollListener ->
            scrollListener.onScroll(scrollX, scrollY)
        }
    }
}