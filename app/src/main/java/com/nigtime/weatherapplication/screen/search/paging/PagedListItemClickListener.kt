/*
 * Сreated by Igor Pokrovsky. 2020/5/17
 */

package com.nigtime.weatherapplication.screen.search.paging

import android.view.GestureDetector
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.nigtime.weatherapplication.domain.location.SearchCity

/**
 * Оптимизированная обработка кликов: не создается OnClickListener на каждый элемент
 */
class PagedListItemClickListener constructor(
    private val recyclerView: RecyclerView,
    private val onItemClick: (SearchCity) -> Unit
) : RecyclerView.OnItemTouchListener {

    private val gestureDetector: GestureDetector

    private val simpleOnGestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return true
        }
    }

    init {
        recyclerView.addOnItemTouchListener(this)
        gestureDetector = GestureDetector(recyclerView.context, simpleOnGestureListener)
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val child = recyclerView.findChildViewUnder(e.x, e.y)
        child?.let {
            child.performClick()
            if (gestureDetector.onTouchEvent(e)) {
                val viewHolder = recyclerView.getChildViewHolder(child)
                val cityViewHolder = viewHolder as SearchCityViewHolder
                onItemClick(cityViewHolder.getCurrentCity())
            }
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        //nothing
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        //nothing
    }
}
