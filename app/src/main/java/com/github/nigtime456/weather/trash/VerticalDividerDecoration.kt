/*
 * Сreated by Igor Pokrovsky. 2020/4/26
 */

package com.github.nigtime456.weather.ui.list

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

/**
 * Реализация [RecyclerView.ItemDecoration] которая рисует цветные разделители в списке.
 *
 * @param color - цвет
 * @param dividerSize - размер разделителя
 */
class VerticalDividerDecoration constructor(
    color: Int,
    private val dividerSize: Int
) : RecyclerView.ItemDecoration() {

    private val bounds = Rect()
    private val divider: Drawable

    init {
        divider = ColorDrawable(color)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        draw(c, parent)
    }

    private fun draw(
        canvas: Canvas,
        parent: RecyclerView
    ) {
        canvas.save()
        val top: Int
        val bottom: Int
        if (parent.clipToPadding) {
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
            canvas.clipRect(
                parent.paddingLeft, top,
                parent.width - parent.paddingRight, bottom
            )
        } else {
            top = 0
            bottom = parent.height
        }

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            parent.layoutManager!!.getDecoratedBoundsWithMargins(child, bounds)
            val right: Int = bounds.right + child.translationX.roundToInt()
            val left: Int = right - dividerSize
            divider.setBounds(left, top, right, bottom)
            divider.draw(canvas)
        }
        canvas.restore()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(0, 0, dividerSize, 0)
    }

}