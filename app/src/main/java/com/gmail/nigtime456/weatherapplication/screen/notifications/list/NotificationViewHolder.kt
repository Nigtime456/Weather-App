/*
 * Сreated by Igor Pokrovsky. 2020/5/23
 */

package com.gmail.nigtime456.weatherapplication.screen.notifications.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import kotlinx.android.synthetic.main.item_notification.view.*

class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindItem(location: SavedLocation) {
        itemView.itemNotificationLocationName.text = location.getName()
        itemView.itemNotificationLocationDescription.text = location.getDescription()
    }

    fun attachSwitchClickListener(onClick: (Int, Boolean) -> Unit) {
        itemView.itemNotificationSwitch.setOnClickListener { view ->
            val isSelect = !view.isSelected
            view.isSelected = isSelect
            onClick(adapterPosition, isSelect)
        }
    }

    fun detachSwitchClickListener() {
        itemView.itemNotificationSwitch.setOnClickListener(null)
    }
}
