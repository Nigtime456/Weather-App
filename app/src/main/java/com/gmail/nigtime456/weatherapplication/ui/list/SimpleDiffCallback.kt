/*
 * Сreated by Igor Pokrovsky. 2020/5/13
 */

package com.gmail.nigtime456.weatherapplication.ui.list

abstract class SimpleDiffCallback<T> {
    abstract fun areItemsTheSame(old: T, new: T): Boolean
    abstract fun areContentsTheSame(old: T, new: T): Boolean
}
