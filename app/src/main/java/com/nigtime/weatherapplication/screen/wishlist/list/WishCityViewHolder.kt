/*
 * Сreated by Igor Pokrovsky. 2020/5/13
 */

package com.nigtime.weatherapplication.screen.wishlist.list

import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nigtime.weatherapplication.domain.city.WishCity
import kotlinx.android.synthetic.main.item_wish_city.view.*

class WishCityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var currentItem: WishCity? = null

    fun bindCity(city: WishCity) {
        currentItem = city
        itemView.itemWishName.text = city.name
        itemView.itemWishStateAndCountry.text = city.getStateAndCounty()
    }

    fun getCurrentItem() = currentItem ?: error("current item == null")

    fun recycle() {
        currentItem = null
    }

    fun attachClickListener(onClick: (Int) -> Unit) {
        itemView.setOnClickListener { onClick(adapterPosition) }
    }

    fun attachDragListener(onStartDrag: (WishCityViewHolder) -> Unit) {
        itemView.itemWishDragArea.setOnTouchListener { _, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    onStartDrag(this@WishCityViewHolder)
                }
            }
            true
        }
    }

    fun removeListeners() {
        itemView.itemWishDragArea.setOnClickListener(null)
        itemView.setOnClickListener(null)
    }

    fun onDragStart() {
        itemView.isSelected = true
    }

    fun onDragEnd() {
        itemView.isSelected = false
    }
}