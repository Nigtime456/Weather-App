/*
 * Сreated by Igor Pokrovsky. 2020/6/4
 */

package com.gmail.nigtime456.weatherapplication.screen.current.fragment.list

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.data.forecast.HourlyForecast
import com.gmail.nigtime456.weatherapplication.data.settings.UnitOfTemp
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_hourly_card.*

class HourlyCardModel(
    private val weatherList: List<HourlyForecast.Weather>,
    private val unitOfTemp: UnitOfTemp
) : AbstractFlexibleItem<HourlyCardModel.ViewHolder>() {

    class ViewHolder(
        override val containerView: View,
        adapter: FlexibleAdapter<out IFlexible<*>>
    ) : FlexibleViewHolder(containerView, adapter),
        LayoutContainer

    private companion object {
        const val MAX_POOL_CAPACITY = 8
    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>,
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>?
    ) {
        holder.itemHourlyCardRecycler.adapter =
            FlexibleAdapter(HourlyWeatherModel.mapList(weatherList, unitOfTemp))
    }

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>
    ): ViewHolder {
        return ViewHolder(view, adapter).also { viewHolder ->
            optimizeNestedRecycler(viewHolder.itemHourlyCardRecycler, adapter.recyclerView)
        }
    }

    private fun optimizeNestedRecycler(
        nestedRecycler: RecyclerView,
        parentRecycler: RecyclerView
    ) {
        nestedRecycler.setRecycledViewPool(parentRecycler.recycledViewPool)
        nestedRecycler.recycledViewPool.setMaxRecycledViews(
            HourlyWeatherModel.VIEW_TYPE,
            MAX_POOL_CAPACITY
        )

        if (nestedRecycler.layoutManager is LinearLayoutManager) {
            (nestedRecycler.layoutManager as LinearLayoutManager)
                .initialPrefetchItemCount = MAX_POOL_CAPACITY
        }
    }

    override fun getLayoutRes(): Int = R.layout.item_hourly_card

    override fun hashCode(): Int {
        var result = weatherList.hashCode()
        result = 31 * result + unitOfTemp.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HourlyCardModel

        if (weatherList != other.weatherList) return false
        if (unitOfTemp != other.unitOfTemp) return false

        return true
    }
}