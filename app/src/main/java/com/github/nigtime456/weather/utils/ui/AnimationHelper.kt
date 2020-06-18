/*
 * Сreated by Igor Pokrovsky. 2020/6/5
 */

package com.github.nigtime456.weather.utils.ui

import android.content.Context
import android.view.View
import androidx.annotation.IntegerRes

object AnimationHelper {

    fun fadeIn(view: View, @IntegerRes durId: Int) {
        view.visibility = View.VISIBLE
        view.alpha = 0f
        view.animate().apply {
            alpha(1f)
            duration = getDuration(view.context, durId)
        }
    }

    private fun getDuration(context: Context, duration: Int): Long {
        return context.getInteger(duration).toLong()
    }
}