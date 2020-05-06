/*
 * Сreated by Igor Pokrovsky. 2020/4/24
 */

package com.nigtime.weatherapplication.common.helper.list

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration constructor(
    context: Context,
    @DimenRes horizontalMargin: Int,
    @DimenRes verticalMargin: Int = 0
) : RecyclerView.ItemDecoration() {
    private var horizontalOffset = 0
    private var verticalOffset = 0

    init {
        if (horizontalMargin != 0) {
            horizontalOffset = context.resources.getDimensionPixelOffset(horizontalMargin)
        }

        if (verticalMargin != 0) {
            verticalOffset = context.resources.getDimensionPixelOffset(verticalMargin)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(horizontalOffset, verticalOffset, horizontalOffset, verticalOffset)
    }
}