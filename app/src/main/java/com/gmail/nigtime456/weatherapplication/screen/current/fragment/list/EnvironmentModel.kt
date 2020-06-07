/*
 * Сreated by Igor Pokrovsky. 2020/6/5
 */

package com.gmail.nigtime456.weatherapplication.screen.current.fragment.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.data.forecast.CurrentForecast
import com.gmail.nigtime456.weatherapplication.data.util.UnitFormatter
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_environment.*

class EnvironmentModel(
    private val environment: CurrentForecast.Environment
) : AbstractFlexibleItem<EnvironmentModel.ViewHolder>() {

    class ViewHolder(
        override val containerView: View,
        adapter: FlexibleAdapter<out IFlexible<*>>?
    ) : FlexibleViewHolder(containerView, adapter),
        LayoutContainer

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>,
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>?
    ) {
        val context = holder.itemView.context

        holder.itemEnvironmentHumidity.text =
            UnitFormatter.formatHumidity(context, environment.humidity)
        holder.itemEnvironmentAirQuality.text =
            UnitFormatter.formatAirQuality(context, environment.airQuality)
        holder.itemEnvironmentUvIndex.text =
            UnitFormatter.formatUvIndex(context, environment.uvIndex)
        holder.itemEnvironmentClouds.text =
            UnitFormatter.formatCloudsCoverage(context, environment.cloudsCoverage)
    }

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>
    ): ViewHolder {
        return ViewHolder(view, adapter)
    }

    override fun getLayoutRes(): Int = R.layout.item_environment

    override fun hashCode(): Int {
        return environment.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EnvironmentModel

        if (environment != other.environment) return false

        return true
    }
}