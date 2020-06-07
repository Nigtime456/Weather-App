/*
 * Сreated by Igor Pokrovsky. 2020/6/4
 */

package com.gmail.nigtime456.weatherapplication.screen.current.fragment.list

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.data.forecast.HourlyForecast
import com.gmail.nigtime456.weatherapplication.data.settings.UnitOfTemp
import com.gmail.nigtime456.weatherapplication.data.util.UnitFormatter
import com.gmail.nigtime456.weatherapplication.tools.ui.DateFormatters
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_hourly_weather.*
import java.text.SimpleDateFormat

class HourlyWeatherModel(
    private val weather: HourlyForecast.Weather,
    private val unitOfTemp: UnitOfTemp
) : AbstractFlexibleItem<HourlyWeatherModel.ViewHolder>() {

    companion object {
        const val VIEW_TYPE = R.layout.item_hourly_weather

        fun mapList(
            list: List<HourlyForecast.Weather>,
            unitOfTemp: UnitOfTemp
        ): List<HourlyWeatherModel> {
            return list.map { weather -> HourlyWeatherModel(weather, unitOfTemp) }
        }
    }

    class ViewHolder(
        override val containerView: View,
        adapter: FlexibleAdapter<out IFlexible<*>>?
    ) : FlexibleViewHolder(containerView, adapter),
        LayoutContainer

    private lateinit var hoursFormatter: SimpleDateFormat

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>?
    ) {
        val context = holder.itemView.context
        initHoursFormatter(context)

        holder.itemHourlyWeatherTime.text = hoursFormatter.format(weather.dateTime)

        holder.itemHourlyWeatherTemp.text =
            UnitFormatter.formatTemp(context, unitOfTemp, weather.temp)
        holder.itemHourlyWeatherIco.setImageResource(weather.ico)
    }

    private fun initHoursFormatter(context: Context) {
        if (::hoursFormatter.isInitialized) {
            return
        }

        hoursFormatter = DateFormatters.getHoursFormatter(context)
    }

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>
    ): ViewHolder {
        return ViewHolder(view, adapter)
    }

    override fun getLayoutRes(): Int = VIEW_TYPE

    override fun hashCode(): Int {
        var result = weather.hashCode()
        result = 31 * result + unitOfTemp.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HourlyWeatherModel

        if (weather != other.weather) return false
        if (unitOfTemp != other.unitOfTemp) return false

        return true
    }
}