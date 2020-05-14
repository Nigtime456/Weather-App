/*
 * Сreated by Igor Pokrovsky. 2020/5/13
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/24
 */

package com.nigtime.weatherapplication.screen.wishlist.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.utility.list.BaseAdapter
import com.nigtime.weatherapplication.common.utility.list.ItemTouchController
import com.nigtime.weatherapplication.common.utility.list.SimpleDiffCallback
import com.nigtime.weatherapplication.domain.city.WishCity

/**
 * TODO загрузка погоды + презентер для адаптера
 *
 * Как должна происходить загрузка данных:
 * К каждому ViewHolder презентер, который сохраняется в VM
 * получается так: ViewModelProvider.get(position,VM)
 *
 */

class WishCitiesListAdapter constructor(private val listener: Listener) :
    BaseAdapter<WishCity, WishCityViewHolder>(DIFF_CALLBACK, true),
    ItemTouchController.TouchAdapter<WishCityViewHolder> {

    interface Listener {
        fun startDragItem(viewHolder: RecyclerView.ViewHolder)
        fun onItemSwiped(swiped: WishCity, position: Int)
        fun onItemsMoved(moved: WishCity, movedPosition: Int, target: WishCity, targetPosition: Int)
        fun onMovementComplete()
        fun onItemClick(position: Int)
    }

    private companion object {
        val DIFF_CALLBACK = object : SimpleDiffCallback<WishCity>() {
            override fun areItemsTheSame(old: WishCity, new: WishCity): Boolean {
                return old.cityId == new.cityId
            }

            override fun areContentsTheSame(old: WishCity, new: WishCity): Boolean {
                return old.cityId == old.cityId && old.listIndex == new.listIndex
            }
        }
    }


    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): WishCityViewHolder {
        return WishCityViewHolder(inflater.inflate(R.layout.item_wish_city, parent, false))
    }


    override fun bindViewHolder(holder: WishCityViewHolder, position: Int, item: WishCity) {
        holder.bindCity(item)
    }

    override fun onViewAttachedToWindow(holder: WishCityViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.attachClickListener(listener::onItemClick)
        holder.attachDragListener(listener::startDragItem)
    }

    override fun onViewDetachedFromWindow(holder: WishCityViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.removeListeners()
    }

    override fun onViewRecycled(holder: WishCityViewHolder) {
        super.onViewRecycled(holder)
        holder.recycle()
    }

    override fun canDropOver(current: WishCityViewHolder, target: WishCityViewHolder): Boolean {
        return true
    }

    override fun dispatchMove(moved: WishCityViewHolder, target: WishCityViewHolder): Boolean {
        listener.onItemsMoved(
            moved.getCurrentItem(),
            moved.adapterPosition,
            target.getCurrentItem(),
            target.adapterPosition
        )
        notifyItemMoved(moved.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onItemSwiped(swiped: WishCityViewHolder) {
        listener.onItemSwiped(swiped.getCurrentItem(), swiped.adapterPosition)
        notifyItemRemoved(swiped.adapterPosition)
    }

    override fun isLongPressDragEnabled(): Boolean = false

    override fun clearItemView(viewHolder: WishCityViewHolder) {
        viewHolder.onDragEnd()
    }

    override fun onMovementCompleted() {
        listener.onMovementComplete()
    }

    override fun onStartDragItem(viewHolder: WishCityViewHolder) {
        viewHolder.onDragStart()
    }
}