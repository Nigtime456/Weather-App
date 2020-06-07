/*
 * Сreated by Igor Pokrovsky. 2020/6/2
 */

package com.gmail.nigtime456.weatherapplication.screen.search.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.data.location.SearchCity
import com.gmail.nigtime456.weatherapplication.tools.ui.ColorSpanHelper
import com.gmail.nigtime456.weatherapplication.tools.ui.getColorFromAttr
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_search.*

class SearchCityModel(val city: SearchCity) : AbstractFlexibleItem<SearchCityModel.ViewHolder>() {

    class ViewHolder(
        override val containerView: View,
        adapter: FlexibleAdapter<out IFlexible<*>>
    ) : FlexibleViewHolder(containerView, adapter), LayoutContainer

    companion object {

        fun mapList(list: List<SearchCity>): List<SearchCityModel> {
            return list.map(::SearchCityModel)
        }
    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>?
    ) {
        val context = holder.itemView.context
        val color = context.getColorFromAttr(R.attr.colorAccent)

        holder.itemSearchName.apply {
            text = ColorSpanHelper.highlightText(city.name, city.query, color)
            //если добавлен - выделить текст
            isEnabled = !city.isSaved
        }

        holder.itemSearchDescription.apply {
            text = city.getStateAndCounty()
            //если добавлен - выделить текст
            isEnabled = !city.isSaved
        }
    }

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>
    ): ViewHolder {
        return ViewHolder(view, adapter)
    }

    override fun getLayoutRes(): Int = R.layout.item_search

    override fun hashCode(): Int {
        return city.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SearchCityModel

        if (city != other.city) return false

        return true
    }
}