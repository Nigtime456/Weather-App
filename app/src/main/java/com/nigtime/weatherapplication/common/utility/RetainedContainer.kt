/*
 * Сreated by Igor Pokrovsky. 2020/5/15
 */

package com.nigtime.weatherapplication.common.utility

class RetainedContainer {
    private val map = mutableMapOf<String, Any>()

    fun contains(key: String): Boolean = map.contains(key)

    fun put(key: String, data: Any) {
        map[key] = data
    }

    fun <T> remove(key: String): T {
        return map.remove(key) as T
    }

    fun <T> getAs(key: String): T {
        return map[key] as T
    }

    fun clear() {
        map.clear()
    }
}