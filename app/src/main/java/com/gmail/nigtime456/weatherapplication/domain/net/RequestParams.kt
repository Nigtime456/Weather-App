/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.gmail.nigtime456.weatherapplication.domain.net

sealed class RequestParams {

    abstract fun getCacheKey(): Long

    data class City(val cityId: Long) : RequestParams() {
        override fun getCacheKey(): Long = cityId
    }
}