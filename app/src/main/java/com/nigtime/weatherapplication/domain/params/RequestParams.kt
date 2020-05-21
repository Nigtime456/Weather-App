/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.domain.params

sealed class RequestParams {
    abstract fun getKey(): Long
    data class City(val cityId: Long) : RequestParams() {
        override fun getKey(): Long = cityId
    }
}