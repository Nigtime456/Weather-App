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
 * @param drawOnLastItem - рисовать ли на последнем элементе
 * @param paddingHorizontal - отступы для дивайдера
 */
class HorizontalDividerDecoration constructor(
    color: Int,
    private val dividerSize: Int,
    private val drawOnLastItem: Boolean = true,
    private val paddingHorizontal: Int = 0
) :
    RecyclerView.ItemDecoration() {

    private val bounds = Rect()
    private val divider: Drawable

    init {
        divider = ColorDrawable(color)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        draw(c, parent)
    }

    private fun draw(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        var left: Int
        var right: Int

        if (parent.clipToPadding) {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            canvas.clipRect(
                left, parent.paddingTop, right,
                parent.height - parent.paddingBottom
            )
        } else {
            left = 0
            right = parent.width
        }
        val childCount = parent.childCount
        val lastItem = childCount - 1

        //set custom padding
        left += paddingHorizontal
        right -= paddingHorizontal

        for (i in 0 until childCount) {
            //drop last item
            if (i == lastItem && !drawOnLastItem)
                break

            val child = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(child, bounds)
            val bottom: Int = bounds.bottom + child.translationY.roundToInt()
            val top: Int = bottom - dividerSize
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
        outRect.set(0, 0, 0, dividerSize)
    }

}