/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.common.helper

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * Вспомогательный класс для отображения тостов,
 * сбрастывает предыдущий тост при показе нового
 */

@Suppress("MemberVisibilityCanBePrivate")
class ToastController constructor(private val context: Context) {
    private var previousToast: Toast? = null

    fun showToast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
        previousToast?.cancel()
        previousToast = Toast.makeText(context, msg, duration)
        previousToast!!.show()

    }

    fun showToast(@StringRes msg: Int, duration: Int = Toast.LENGTH_SHORT) {
        val str = context.getString(msg)
        showToast(str, duration)
    }
}