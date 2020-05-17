/*
 * Сreated by Igor Pokrovsky. 2020/5/13
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/24
 */

package com.nigtime.weatherapplication.screen.savedlocations.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.utility.list.BaseAdapter
import com.nigtime.weatherapplication.common.utility.list.ItemTouchController
import com.nigtime.weatherapplication.common.utility.list.SimpleDiffCallback
import com.nigtime.weatherapplication.domain.location.SavedLocation

/**
 * TODO загрузка погоды + презентер для адаптера
 *
 * Как должна происходить загрузка данных:
 * К каждому ViewHolder презентер, который сохраняется в VM
 * получается так: ViewModelProvider.get(position,VM)
 *
 */

class SavedLocationsAdapter constructor(
    private val listener: Listener,
    private val presenterFactory: ItemPresenterFactory
) :
    BaseAdapter<SavedLocation, SavedLocationViewHolder>(DIFF_CALLBACK, true),
    ItemTouchController.TouchAdapter<SavedLocationViewHolder> {

    interface Listener {
        fun startDragItem(viewHolder: RecyclerView.ViewHolder)
        fun onItemSwiped(swiped: SavedLocation, position: Int)
        fun onItemsMoved(
            moved: SavedLocation,
            movedPosition: Int,
            target: SavedLocation,
            targetPosition: Int
        )

        fun onMovementComplete()
        fun onItemClick(position: Int)
    }

    private companion object {
        val DIFF_CALLBACK = object : SimpleDiffCallback<SavedLocation>() {
            override fun areItemsTheSame(old: SavedLocation, new: SavedLocation): Boolean {
                return old.areItemsTheSame(new)
            }

            override fun areContentsTheSame(old: SavedLocation, new: SavedLocation): Boolean {
                return old.areContentsTheSame(new)
            }
        }
    }

    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): SavedLocationViewHolder {
        return SavedLocationViewHolder(
            inflater.inflate(
                R.layout.item_saved_location,
                parent,
                false
            )
        )
    }


    override fun bindViewHolder(
        holder: SavedLocationViewHolder,
        position: Int,
        item: SavedLocation
    ) {
        holder.bindLocation(item)
        holder.presenter = presenterFactory.getItemPresenter(item)
    }

    override fun onViewAttachedToWindow(holder: SavedLocationViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.attachOnClickListener(listener::onItemClick)
        holder.attachDragListener(listener::startDragItem)
        holder.attachPresenter()
    }

    override fun onViewDetachedFromWindow(holder: SavedLocationViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.removeListeners()
        holder.detachPresenter()
    }

    override fun onViewRecycled(holder: SavedLocationViewHolder) {
        super.onViewRecycled(holder)
        holder.recycle()
    }

    override fun canDropOver(
        current: SavedLocationViewHolder,
        target: SavedLocationViewHolder
    ): Boolean {
        return true
    }

    override fun isLongPressDragEnabled(): Boolean = false

    override fun dispatchMove(
        moved: SavedLocationViewHolder,
        target: SavedLocationViewHolder
    ): Boolean {
        listener.onItemsMoved(
            moved.getCurrentItem(),
            moved.adapterPosition,
            target.getCurrentItem(),
            target.adapterPosition
        )
        notifyItemMoved(moved.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onItemSwiped(swiped: SavedLocationViewHolder) {
        listener.onItemSwiped(swiped.getCurrentItem(), swiped.adapterPosition)
        notifyItemRemoved(swiped.adapterPosition)
    }

    override fun clearItemView(viewHolder: SavedLocationViewHolder) {
        viewHolder.onDragEnd()
    }

    override fun onMovementComplete() {
        listener.onMovementComplete()
    }

    override fun onStartDragItem(viewHolder: SavedLocationViewHolder) {
        viewHolder.onDragStart()
    }
}