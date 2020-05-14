/*
 * Сreated by Igor Pokrovsky. 2020/4/25
 */


package com.nigtime.weatherapplication.common.animation

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar

//TODO useless
class SnackbarTranslationBehavior constructor(context: Context, attributes: AttributeSet) :
    CoordinatorLayout.Behavior<View>() {


    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency is Snackbar.SnackbarLayout
    }


    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        //костыль
        if (dependency.translationY == 0f)
            return true

        val transY = 0f.coerceAtMost(dependency.translationY - dependency.height)
        child.translationY = transY
        return true
    }
}