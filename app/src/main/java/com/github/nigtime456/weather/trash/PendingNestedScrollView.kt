/*
 * Сreated by Igor Pokrovsky. 2020/5/9
 */

package com.github.nigtime456.weather.trash

import android.content.Context
import android.util.AttributeSet
import androidx.core.widget.NestedScrollView

/**
 * NestedScrollView который откладывает установку скролла, если layout ещё не
 * сформирован, тем самым скролл не будет "проглочен".
 */
class PendingNestedScrollView : NestedScrollView {

    private var pendingScrollY = -1
    private var hasLayout = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    fun delayScrollY(scrollY: Int) {
        if (this.scrollY == scrollY)
            return

        if (hasLayout) {
            this.scrollY = scrollY
        } else {
            //layout ещё не сформирован, откладываем скроллл
            pendingScrollY = scrollY
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        hasLayout = true
        scrollY = pendingScrollY
        pendingScrollY = -1
    }

}