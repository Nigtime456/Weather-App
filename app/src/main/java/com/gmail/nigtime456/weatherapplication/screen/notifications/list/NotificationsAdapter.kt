/*
 * Сreated by Igor Pokrovsky. 2020/5/23
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/23
 */

package com.gmail.nigtime456.weatherapplication.screen.notifications.list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.common.list.BaseAdapter
import com.gmail.nigtime456.weatherapplication.common.list.SimpleDiffCallback
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation


class NotificationsAdapter : BaseAdapter<SavedLocation, NotificationViewHolder>(DIFF_CALLBACK) {

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

    override fun onViewAttachedToWindow(holder: NotificationViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.attachSwitchClickListener { i, b ->
            Log.d("sas", "i = $i select = $b")
        }
    }

    override fun onViewDetachedFromWindow(holder: NotificationViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.detachSwitchClickListener()
    }

    override fun bindViewHolder(
        holder: NotificationViewHolder,
        position: Int,
        item: SavedLocation
    ) {
        holder.bindItem(item)
    }

    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): NotificationViewHolder {
        return NotificationViewHolder(inflater.inflate(R.layout.item_notification, parent, false))
    }
}