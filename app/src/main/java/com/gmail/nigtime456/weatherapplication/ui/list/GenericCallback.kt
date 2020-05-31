/*
 * Сreated by Igor Pokrovsky. 2020/5/13
 */

package com.gmail.nigtime456.weatherapplication.ui.list

import androidx.recyclerview.widget.DiffUtil

class GenericCallback<T> constructor(
    private val oldList: List<T>,
    private val newList: List<T>,
    private val itemsCallback: SimpleDiffCallback<T>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return itemsCallback.areItemsTheSame(old, new)
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return itemsCallback.areContentsTheSame(old, new)
    }

}