/*
 * Сreated by Igor Pokrovsky. 2020/6/5
 */

package com.gmail.nigtime456.weatherapplication.screen.current.fragment.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.data.forecast.CurrentForecast
import com.gmail.nigtime456.weatherapplication.data.settings.UnitOfLength
import com.gmail.nigtime456.weatherapplication.data.settings.UnitOfPressure
import com.gmail.nigtime456.weatherapplication.data.settings.UnitOfSpeed
import com.gmail.nigtime456.weatherapplication.data.util.UnitFormatter
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_detailed_weather.*

class DetailedWeatherModel(
    private val detailedWeather: CurrentForecast.DetailedWeather,
    private val unitOfSpeed: UnitOfSpeed,
    private val unitOfPressure: UnitOfPressure,
    private val unitOfLength: UnitOfLength
) : AbstractFlexibleItem<DetailedWeatherModel.ViewHolder>() {

    class ViewHolder(
        override val containerView: View,
        adapter: FlexibleAdapter<out IFlexible<*>>
    ) : FlexibleViewHolder(containerView, adapter),
        LayoutContainer

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>,
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>?
    ) {
        val context = holder.itemView.context
        val wind = detailedWeather.wind

        holder.itemDetailedWeatherDirChars.setText(wind.cardinalDirection.getAbbreviatedName())
        holder.itemDetailedWeatherWindSpeed.text =
            UnitFormatter.formatSpeed(context, unitOfSpeed, wind.speed)
        holder.itemDetailedWeatherWindIndicator.rotation = wind.degrees.toFloat()

        holder.itemDetailedWeatherVisibility.text =
            UnitFormatter.formatVisibility(context, unitOfLength, detailedWeather.visibility)

        holder.itemDetailedWeatherPressure.text =
            UnitFormatter.formatPressure(context, unitOfPressure, detailedWeather.pressure)
    }

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>
    ): ViewHolder {
        return ViewHolder(view, adapter)
    }

    override fun getLayoutRes(): Int = R.layout.item_detailed_weather

    override fun hashCode(): Int {
        var result = detailedWeather.hashCode()
        result = 31 * result + unitOfSpeed.hashCode()
        result = 31 * result + unitOfPressure.hashCode()
        result = 31 * result + unitOfLength.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DetailedWeatherModel

        if (detailedWeather != other.detailedWeather) return false
        if (unitOfSpeed != other.unitOfSpeed) return false
        if (unitOfPressure != other.unitOfPressure) return false
        if (unitOfLength != other.unitOfLength) return false

        return true
    }
}