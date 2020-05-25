/*
 * Сreated by Igor Pokrovsky. 2020/4/26
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.gmail.nigtime456.weatherapplication.trash

import android.content.Context
import android.text.SpannableString
import android.text.style.ImageSpan
import androidx.annotation.DrawableRes
import java.util.*

/**
 * Вспомогательный класс для добавления изображений в текст, с помощью SpannableString
 *
 */
//TODO useless
class ImageSpanHelper {

    companion object {
        fun setImage(
            context: Context,
            source: String,
            placeHolder: String,
            @DrawableRes resId: Int
        ): SpannableString {
            val sourceStr = source.toLowerCase(Locale.getDefault())
            val substringStr = placeHolder.toLowerCase(Locale.getDefault())

            var startIndex = sourceStr.indexOf(substringStr)
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