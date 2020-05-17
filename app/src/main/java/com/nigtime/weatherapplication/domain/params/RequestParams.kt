/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.domain.params

sealed class RequestParams {
    data class City(val cityId: Long) : RequestParams()
}