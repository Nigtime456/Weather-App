/*
 * Сreated by Igor Pokrovsky. 2020/5/2
 */

package com.gmail.nigtime456.weatherapplication.ui.list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.nigtime456.weatherapplication.tools.rx.RxAsyncDiffer

abstract class BaseAdapter<T, VH : RecyclerView.ViewHolder> constructor(
    private val diffCallback: SimpleDiffCallback<T>,
    private val rxAsyncDiffer: RxAsyncDiffer,
    private val detectMoves: Boolean = false
) : RecyclerView.Adapter<VH>() {

    private var currentList = emptyList<T>()

    fun submitList(
        newList: List<T>,
        calculateDiffs: Boolean = true,
        commitCallback: () -> Unit = {}
    ) {
        if (calculateDiffs && currentList.isNotEmpty()) {
            Log.d("sas", "[${javaClass.name}] commit")
            calculateDiffs(newList, commitCallback)
        } else {
            Log.d("sas", "[${javaClass.name}] commitImmediately")
            commitImmediately(newList, commitCallback)
        }
    }

    private fun calculateDiffs(
        newList: List<T>,
        commitCallback: () -> Unit
    ) {
        val genericCallback = GenericCallback(currentList, newList, diffCallback)
        rxAsyncDiffer.submitList(genericCallback, detectMoves) { diffResult ->
            currentList = newList
            diffResult.dispatchUpdatesTo(this)
            commitCallback()
        }
    }

    private fun commitImmediately(
        newList: List<T>,
        commitCallback: () -> Unit
    ) {
        currentList = newList
        notifyDataSetChanged()
        commitCallback()
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