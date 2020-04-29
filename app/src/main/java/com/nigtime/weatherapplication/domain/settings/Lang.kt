/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.domain.settings

enum class Lang {
    EN, RU;

    fun getCode() = when (this) {
        EN -> "en"
        RU -> "ru"
    }

    fun fromCode(string: String) = when (string) {
        "en" -> EN
        "ru" -> RU
        else -> error("invalid code <$string>")
    }
}