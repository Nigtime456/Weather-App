/*
 * Сreated by Igor Pokrovsky. 2020/6/4
 */

package com.gmail.nigtime456.weatherapplication.screen.current.fragment.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.data.forecast.CurrentForecast
import com.gmail.nigtime456.weatherapplication.data.settings.UnitOfTemp
import com.gmail.nigtime456.weatherapplication.data.util.UnitFormatter
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_current_weather.*

class CurrentWeatherModel(
    private val weather: CurrentForecast.Weather,
    private val unitOfTemp: UnitOfTemp
) : AbstractFlexibleItem<CurrentWeatherModel.ViewHolder>() {

    class ViewHolder(
        override val containerView: View,
        adapter: FlexibleAdapter<out IFlexible<*>>
    ) : FlexibleViewHolder(containerView, adapter),
        LayoutContainer

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>?
    ) {
        val context = holder.itemView.context

        holder.itemCurrentTemp.text =
            UnitFormatter.formatTemp(context, unitOfTemp, weather.temp)
        holder.itemCurrentFeelsLikeTemp.text =
            UnitFormatter.formatFeelsLikeTemp(context, unitOfTemp, weather.feelsLikeTemp)

        holder.itemCurrentIco.setImageResource(weather.ico)
        holder.itemCurrentDescription.setText(weather.description)
    }

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>
    ): ViewHolder {
        return ViewHolder(view, adapter)
    }

    override fun getLayoutRes(): Int = R.layout.item_current_weather

    override fun hashCode(): Int {
        var result = weather.hashCode()
        result = 31 * result + unitOfTemp.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CurrentWeatherModel

        if (weather != other.weather) return false
        if (unitOfTemp != other.unitOfTemp) return false

        return true
    }
}