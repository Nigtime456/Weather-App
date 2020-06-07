package com.gmail.nigtime456.weatherapplication.screen.locations.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.data.location.SavedLocation
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import kotlinx.android.synthetic.main.item_location.*

class LocationModel(
    val location: SavedLocation,
    private val presenterFactory: LocationViewHolderPresenterFactory,
    private val listener: OnClickListener
) : AbstractFlexibleItem<LocationViewHolder>() {

    companion object {
        fun mapList(
            list: List<SavedLocation>,
            presenterFactory: LocationViewHolderPresenterFactory,
            listener: OnClickListener
        ): List<LocationModel> {
            return list.map { location ->
                LocationModel(location, presenterFactory, listener)
            }
        }
    }

    interface OnClickListener {
        fun onClickItem(position: Int)
        fun onClickDeleteItem(position: Int)
    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>,
        holder: LocationViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        holder.itemLocationName.text = location.getName()
        holder.itemLocationDescription.text = location.getDescription()

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
        super.onViewAttached(adapter, holder, position)
        holder.itemLocationDelete.setOnClickListener {
            listener.onClickDeleteItem(holder.flexibleAdapterPosition)
            holder.itemLocationSwipeLayout.close()
        }

        holder.itemLocationItemGroup.setOnClickListener {
            listener.onClickItem(holder.flexibleAdapterPosition)
        }

        holder.presenter = presenterFactory.getItemPresenter(location)
        holder.attachPresenter()
    }

    override fun onViewDetached(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: LocationViewHolder,
        position: Int
    ) {
        super.onViewDetached(adapter, holder, position)
        holder.itemLocationDelete.setOnClickListener(null)
        holder.itemLocationItemGroup.setOnClickListener(null)
        holder.itemLocationSwipeLayout.setOnActionsListener(null)
        holder.itemLocationSwipeLayout.close()
        holder.detachPresenter()
    }

    override fun getLayoutRes(): Int = R.layout.item_location

    override fun hashCode(): Int {
        var result = location.hashCode()
        result = 31 * result + presenterFactory.hashCode()
        result = 31 * result + listener.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LocationModel

        if (location != other.location) return false

        return true
    }

}