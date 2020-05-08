/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.domain.param

sealed class RequestParams {
    data class CityParams(val cityId: Long) : RequestParams()
}