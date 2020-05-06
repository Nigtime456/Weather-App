/*
 * Сreated by Igor Pokrovsky. 2020/4/26
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.common.helper

import android.content.Context
import android.text.SpannableString
import android.text.style.ImageSpan
import androidx.annotation.DrawableRes

/**
 * Вспомогательный класс для добавления изображений в текст, с помощью SpannableString
 *
 */
class ImageSpanHelper {

    companion object {
        fun setImage(
            context: Context,
            source: String,
            placeHolder: String, @DrawableRes resId: Int
        ): SpannableString {
            var startIndex = source.indexOf(placeHolder)
            var endIndex = startIndex + placeHolder.length

            if (startIndex == -1)
                startIndex = 0
            if (endIndex == -1)
                endIndex = 0

            return if (startIndex == 0 && endIndex == 0) {
                SpannableString(source)
            } else {
                SpannableString(source).apply {
                    setSpan(
                        ImageSpan(context, resId),
                        startIndex,
                        endIndex,
                        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }

        }
    }
}