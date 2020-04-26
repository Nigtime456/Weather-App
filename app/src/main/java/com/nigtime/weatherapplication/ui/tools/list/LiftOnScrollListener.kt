/*
 * Сreated by Igor Pokrovsky. 2020/4/26
 */


package com.nigtime.weatherapplication.ui.tools.list

import androidx.recyclerview.widget.RecyclerView

/**
 * Вспомогательный класс для изменения высоты аппбара при скролле (lift on scroll appbar)
 *
 * @param doLift - true - увеличить высоту, false - уменьшить
 *
 */

class LiftOnScrollListener constructor(private val doLift: (Boolean) -> Unit) :
    RecyclerView.OnScrollListener() {
    private var totalScroll = 0
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        totalScroll += dy

        if (totalScroll == 0)
            doLift(false)
        else
            doLift(true)
    }
}