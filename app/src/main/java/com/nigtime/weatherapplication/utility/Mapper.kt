/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.utility

interface Mapper<I, O> {
    fun map(input: I): O
}