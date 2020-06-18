/*
 * Сreated by Igor Pokrovsky. 2020/6/15
 */

package com.github.nigtime456.weather.customview

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.drawable.Animatable
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.github.nigtime456.weather.R
import com.github.nigtime456.weather.utils.ui.RelativeTimeUtils
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState

class RefreshHeader : ClassicsHeader {

    private companion object {
        const val NO_TIME = -1L
    }

    private var updateTime = NO_TIME

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {
        //remove, to manage it yourself
        mLastTime = null
    }

    override fun onStartAnimator(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
        val progressView: View = mProgressView
        if (progressView.visibility != View.VISIBLE) {
            progressView.visibility = View.VISIBLE
            val drawable = mProgressView.drawable
            if (drawable is Animatable) {
                (drawable as Animatable).start()
            } else {
                //change animation
                ObjectAnimator.ofFloat(progressView, View.ROTATION, 0f, 360f).apply {
                    repeatCount = ObjectAnimator.INFINITE
                    repeatMode = ObjectAnimator.RESTART
                    duration = 3000
                    interpolator = LinearInterpolator()
                    start()
                }
            }
        }
    }

    fun setUpdateTime(time: Long) {
        updateTime = time
    }

    override fun onStateChanged(
        refreshLayout: RefreshLayout,
        oldState: RefreshState,
        newState: RefreshState
    ) {
        super.onStateChanged(refreshLayout, oldState, newState)
        //update text when pulling
        if (newState == RefreshState.PullDownToRefresh) {
            if (updateTime != NO_TIME) {
                mLastUpdateText.text = RelativeTimeUtils.formatTime(context, updateTime)
            } else {
                mLastUpdateText.setText(R.string.update_data)
            }
        }
    }

}