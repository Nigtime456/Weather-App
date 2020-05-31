/*
 * Сreated by Igor Pokrovsky. 2020/5/13
 */


package com.gmail.nigtime456.weatherapplication.ui.screen.locations.list

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.tools.rx.RxAsyncDiffer
import com.gmail.nigtime456.weatherapplication.ui.list.BaseAdapter
import com.gmail.nigtime456.weatherapplication.ui.list.ItemTouchController
import com.gmail.nigtime456.weatherapplication.ui.list.SimpleDiffCallback
import kotlinx.android.synthetic.main.item_saved_location.view.*


class LocationsAdapter constructor(
    private val listener: Listener,
    private val presenterFactory: LocationItemPresenterFactory,
    rxAsyncDiffer: RxAsyncDiffer
) : BaseAdapter<SavedLocation, LocationViewHolder>(DIFF_CALLBACK, rxAsyncDiffer, true),
    ItemTouchController.TouchAdapter<LocationViewHolder> {

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
    ): LocationViewHolder {
        val view = inflater.inflate(R.layout.item_saved_location, parent, false)
        return LocationViewHolder(view)
    }


    override fun bindViewHolder(
        holder: LocationViewHolder,
        position: Int,
        item: SavedLocation
    ) {
        holder.bindLocation(item)
        holder.presenter = presenterFactory.getItemPresenter(item)
    }

    override fun onViewAttachedToWindow(holder: LocationViewHolder) {
        super.onViewAttachedToWindow(holder)
        bindClickListener(holder)
        bindDragListener(holder)
        holder.loadForecast()
    }

    private fun bindClickListener(holder: LocationViewHolder) {
        holder.itemView.setOnClickListener {
            listener.onItemClick(holder.adapterPosition)
        }
    }

    private fun bindDragListener(holder: LocationViewHolder) {
        holder.itemView.itemSavedLocationDragArea.setOnTouchListener { _, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    listener.startDragItem(holder)
                }
            }
            true
        }
    }

    override fun onViewDetachedFromWindow(holder: LocationViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.setOnClickListener(null)
        holder.itemView.itemSavedLocationDragArea.setOnTouchListener(null)
        holder.detachPresenter()
    }

    override fun canDropOver(
        current: LocationViewHolder,
        target: LocationViewHolder
    ): Boolean {
        return true
    }

    override fun isLongPressDragEnabled(): Boolean = false

    override fun dispatchMove(
        moved: LocationViewHolder,
        target: LocationViewHolder
    ): Boolean {
        listener.onItemsMoved(
            getItem(moved.adapterPosition),
            moved.adapterPosition,
            getItem(target.adapterPosition),
            target.adapterPosition
        )
        notifyItemMoved(moved.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onItemSwiped(swiped: LocationViewHolder) {
        listener.onItemSwiped(getItem(swiped.adapterPosition), swiped.adapterPosition)
        notifyItemRemoved(swiped.adapterPosition)
    }

    override fun clearItemView(viewHolder: LocationViewHolder) {
        viewHolder.itemView.isSelected = false
    }

    override fun onMovementComplete() {
        listener.onMovementComplete()
    }

    override fun onStartDragItem(viewHolder: LocationViewHolder) {
        viewHolder.itemView.isSelected = true
    }
}