/*
 * Сreated by Igor Pokrovsky. 2020/5/2
 */

package com.nigtime.weatherapplication.common.utility.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nigtime.weatherapplication.common.App
import com.nigtime.weatherapplication.common.rx.RxAsyncDiffer

abstract class BaseAdapter<T, VH : RecyclerView.ViewHolder> constructor(
    private val diffCallback: SimpleDiffCallback<T>,
    private val detectMoves: Boolean = false,
    private val rxAsyncDiffer: RxAsyncDiffer = App.INSTANCE.appContainer.getRxAsyncDiffer()
) :
    RecyclerView.Adapter<VH>() {


    private var currentList = emptyList<T>()

    fun submitList(newList: List<T>, calculateDiffs: Boolean) {
        if (calculateDiffs) {
            calculateDiffs(newList)
        } else {
            commitImmediately(newList)
        }
    }

    private fun calculateDiffs(newList: List<T>) {
        val genericCallback = GenericCallback(currentList, newList, diffCallback)
        rxAsyncDiffer.submitList(genericCallback, detectMoves) { diffResult ->
            currentList = newList
            diffResult.dispatchUpdatesTo(this)
        }
    }

    private fun commitImmediately(newList: List<T>) {
        currentList = newList
        notifyDataSetChanged()
    }

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        return createViewHolder(inflater, parent, viewType)
    }

    final override fun onBindViewHolder(holder: VH, position: Int) {
        bindViewHolder(holder, position, getItem(position))
    }

    final override fun getItemCount(): Int = currentList.size

    fun getItem(position: Int): T = currentList[position]

    abstract fun bindViewHolder(holder: VH, position: Int, item: T)

    abstract fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): VH
}