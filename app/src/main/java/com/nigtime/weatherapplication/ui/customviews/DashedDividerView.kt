/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.ui.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.nigtime.weatherapplication.R

/**
 * View рисующий пунктирную линию.
 */
class DashedDividerView : View {


    private companion object {
        const val VERTICAL = 0
        const val HORIZONTAL = 1

    }

    private var orientation = HORIZONTAL
    private val paint = Paint()
    private val path = Path()

    constructor(context: Context) : super(context, null, 0) {
        init(context, null)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init(context, attributeSet)
    }


    private fun init(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DashedDividerView, 0, 0)

        val gap = typedArray.getDimension(R.styleable.DashedDividerView_dividerGap, 5.25f)
        val width = typedArray.getDimension(R.styleable.DashedDividerView_dividerSize, 5.25f)
        val color = typedArray.getColor(R.styleable.DashedDividerView_dividerColor, Color.CYAN)
        orientation =
            typedArray.getInt(R.styleable.DashedDividerView_dividerOrientation, HORIZONTAL)

        paint.color = color
        paint.style = Paint.Style.STROKE
        paint.pathEffect = DashPathEffect(floatArrayOf(width, gap), 0f)
        paint.strokeWidth = width

        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        path.moveTo(0f, 0f)

        if (orientation == VERTICAL) {
            path.lineTo(0f, height.toFloat())
        } else {
            path.lineTo(width.toFloat(), 0f)
        }
        canvas.drawPath(path, paint)
    }

}