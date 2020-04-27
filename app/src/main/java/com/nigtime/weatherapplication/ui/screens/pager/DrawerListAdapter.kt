/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.ui.screens.pager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.db.data.CityForForecastData
import kotlinx.android.synthetic.main.item_drawer_list_city.view.*

class DrawerListAdapter constructor(private val onClick: (Int) -> Unit) :
    RecyclerView.Adapter<DrawerListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: CityForForecastData, isDisabled: Boolean) {
            itemView.itemDrawerListCityName.text = item.cityName
            //false == enabled, true == disabled
            itemView.isActivated = isDisabled
        }
    }

    private var items = emptyList<CityForForecastData>()
    private var currentActivated = 0

    fun submitList(items: List<CityForForecastData>) {
        this.items = items
        //данный список не планируется обновлять между onCreateView() и onDestroyView()
        notifyDataSetChanged()
    }

    fun setActivatedItem(position: Int) {
        val previous = currentActivated
        currentActivated = position
        notifyItemChanged(previous)
        notifyItemChanged(currentActivated)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener { onClick(holder.adapterPosition) }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.setOnClickListener(null)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_drawer_list_city, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position != currentActivated)
    }
}