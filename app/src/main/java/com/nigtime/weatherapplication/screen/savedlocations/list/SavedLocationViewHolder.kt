/*
 * Сreated by Igor Pokrovsky. 2020/5/13
 */

package com.nigtime.weatherapplication.screen.savedlocations.list

import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nigtime.weatherapplication.domain.location.SavedLocation
import kotlinx.android.synthetic.main.item_saved_location.view.*

class SavedLocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var currentLocation: SavedLocation? = null

    fun bindLocation(location: SavedLocation) {
        currentLocation = location
        itemView.itemSavedLocationName.text = location.getName()
        itemView.itemSavedLocationDescription.text = location.getDescription()
    }

    fun getCurrentItem() = currentLocation ?: error("current item == null")

    fun recycle() {
        currentLocation = null
    }

    fun attachOnClickListener(onClick: (Int) -> Unit) {
        itemView.setOnClickListener { onClick(adapterPosition) }
    }

    fun attachDragListener(onStartDrag: (SavedLocationViewHolder) -> Unit) {
        itemView.itemSavedLocation.setOnTouchListener { _, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    onStartDrag(this@SavedLocationViewHolder)
                }
            }
            true
        }
    }

    fun removeListeners() {
        itemView.itemSavedLocation.setOnClickListener(null)
        itemView.setOnClickListener(null)
    }

    fun onDragStart() {
        itemView.isSelected = true
    }

    fun onDragEnd() {
        itemView.isSelected = false
    }
}