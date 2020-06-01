/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

package com.gmail.nigtime456.weatherapplication.domain.settings

import java.util.*

//TODO unused
sealed class Lang {
    abstract fun getLangCode(): String
    abstract fun getLocale(): Locale

    object RU : Lang() {
        override fun getLangCode(): String = "ru"

        override fun getLocale(): Locale = Locale(getLangCode())
    }

    object EN : Lang() {
        override fun getLangCode(): String = "en"

        override fun getLocale(): Locale = Locale(getLangCode())
    }
}