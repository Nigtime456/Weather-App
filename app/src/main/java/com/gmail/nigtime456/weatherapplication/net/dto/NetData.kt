/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

package com.gmail.nigtime456.weatherapplication.net.dto

data class NetData<T>(val loadTimestamp: Long, val data: T)