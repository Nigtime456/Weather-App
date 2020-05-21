/*
 * Сreated by Igor Pokrovsky. 2020/5/13
 */

package com.nigtime.weatherapplication.screen.savedlocations.list

import android.view.MotionEvent
import android.view.View
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.nigtime.weatherapplication.domain.location.SavedLocation
import com.nigtime.weatherapplication.domain.settings.UnitOfTemp
import com.nigtime.weatherapplication.domain.utility.UnitFormatHelper
import kotlinx.android.synthetic.main.item_saved_location.view.*

class SavedLocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    SavedLocationItemView {
    private var currentLocation: SavedLocation? = null
    private val unitFormatHelper: UnitFormatHelper = UnitFormatHelper(itemView.context)

    var presenter: SavedLocationItemPresenter? = null

    fun bindLocation(location: SavedLocation) {
        currentLocation = location
        itemView.itemSavedLocationName.text = location.getName()
        itemView.itemSavedLocationDescription.text = location.getDescription()
    }


    fun getCurrentItem() = currentLocation ?: error("current item == null")

    fun recycle() {
        currentLocation = null
        presenter = null
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

    fun attachPresenter() {
        presenter?.attach(this)
    }

    fun detachPresenter() {
        presenter?.detach()
    }

    override fun showCurrentTemp(temp: Double, unitOfTemp: UnitOfTemp) {
        itemView.itemSavedLocationTemp.text = unitFormatHelper.formatTemp(unitOfTemp, temp)
    }

    override fun showCurrentTempIco(@DrawableRes ico: Int) {
        itemView.itemSavedLocationTempIco.setImageResource(ico)
    }
}