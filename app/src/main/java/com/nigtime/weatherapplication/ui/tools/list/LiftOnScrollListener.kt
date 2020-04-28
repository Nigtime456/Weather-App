/*
 * Сreated by Igor Pokrovsky. 2020/4/26
 */


package com.nigtime.weatherapplication.ui.tools.list

import android.view.ViewTreeObserver
import androidx.recyclerview.widget.RecyclerView

/**
 * Вспомогательный класс для изменения высоты аппбара при скролле (lift on scroll appbar)
 *
 * @param doLift - true - увеличить высоту, false - уменьшить
 *
 */

class LiftOnScrollListener constructor(
    private var recyclerView: RecyclerView?,
    private val doLift: (Boolean) -> Unit
) :
    RecyclerView.OnScrollListener(), ViewTreeObserver.OnScrollChangedListener {
    private var totalScroll = 0

    init {
        // recyclerView.addOnScrollListener(this)
        recyclerView!!.viewTreeObserver.addOnScrollChangedListener(this)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        totalScroll += dy

        if (totalScroll == 0)
            doLift(false)
        else
            doLift(true)
    }

    override fun onScrollChanged() {
        val scrollOffset = recyclerView!!.computeVerticalScrollOffset()
        if (scrollOffset == 0)
            doLift(false)
        else
            doLift(true)
    }

    fun release() {
        recyclerView!!.viewTreeObserver.removeOnScrollChangedListener(this)
        recyclerView = null
    }

}