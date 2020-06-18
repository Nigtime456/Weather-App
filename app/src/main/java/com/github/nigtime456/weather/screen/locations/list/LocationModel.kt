package com.github.nigtime456.weather.screen.locations.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.nigtime456.weather.R
import com.github.nigtime456.weather.data.location.SavedLocation
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import kotlinx.android.synthetic.main.item_location.*

class LocationModel(
    val location: SavedLocation,
    private val presenterFactory: LocationViewHolderPresenterFactory
) : AbstractFlexibleItem<LocationViewHolder>() {

    companion object {
        const val VIEW_TYPE = R.layout.item_location

        fun mapList(
            list: List<SavedLocation>,
            presenterFactory: LocationViewHolderPresenterFactory
        ): List<LocationModel> {
            return list.map { location ->
                LocationModel(location, presenterFactory)
            }
        }
    }

    private var isOpened = false

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>,
        holder: LocationViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        holder.item_location_name.text = location.name
        holder.item_location_description.text = location.getStateAndCounty()

    }

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>
    ): LocationViewHolder {
        return LocationViewHolder(view, adapter)
    }

    override fun onViewAttached(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: LocationViewHolder,
        position: Int
    ) {
        if (isOpened) {
            holder.item_location_swipe_layout.open(false)
        }
        holder.presenter = presenterFactory.getItemPresenter(location)
        holder.attachPresenter()
    }

    override fun onViewDetached(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: LocationViewHolder,
        position: Int
    ) {
        isOpened = holder.item_location_swipe_layout.isOpened
        holder.item_location_swipe_layout.close(false)
        holder.detachPresenter()
    }

    override fun getLayoutRes(): Int = VIEW_TYPE

    override fun hashCode(): Int {
        var result = location.hashCode()
        result = 31 * result + presenterFactory.hashCode()
        result = 31 * result + isOpened.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LocationModel

        if (location != other.location) return false
        if (presenterFactory != other.presenterFactory) return false
        if (isOpened != other.isOpened) return false

        return true
    }

}