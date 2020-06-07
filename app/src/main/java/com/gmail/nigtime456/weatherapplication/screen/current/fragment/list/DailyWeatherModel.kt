/*
 * Сreated by Igor Pokrovsky. 2020/6/5
 */

package com.gmail.nigtime456.weatherapplication.screen.current.fragment.list

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.data.forecast.DailyForecast
import com.gmail.nigtime456.weatherapplication.data.settings.UnitOfTemp
import com.gmail.nigtime456.weatherapplication.data.util.UnitFormatter
import com.gmail.nigtime456.weatherapplication.tools.ui.DateFormatters
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_daily_weather.*

class DailyWeatherModel(
    private val weather: DailyForecast.Weather,
    private val unitOfTemp: UnitOfTemp
) : AbstractFlexibleItem<DailyWeatherModel.ViewHolder>() {

    private val weekDayFormatter = DateFormatters.getWeekdayFormatter()

    companion object {
        const val VIEW_TYPE = R.layout.item_daily_weather

        fun mapList(
            list: List<DailyForecast.Weather>,
            unitOfTemp: UnitOfTemp
        ): List<DailyWeatherModel> {
            return list.map { weather -> DailyWeatherModel(weather, unitOfTemp) }
        }
    }

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

        holder.itemDailyWeatherCurrentDate.text = formatDate(context)

        holder.itemDailyWeatherDTemp.text =
            UnitFormatter.formatTemp(context, unitOfTemp, weather.maxTemp)
        holder.itemDailyWeatherNTemp.text =
            UnitFormatter.formatTemp(context, unitOfTemp, weather.minTemp)

        holder.itemDailyWeatherIco.setImageResource(weather.ico)
    }

    private fun formatDate(context: Context): String {
        return when (weather.index) {
            0 -> context.getString(R.string.units_today)
            1 -> context.getString(R.string.units_tomorrow)
            else -> weekDayFormatter.format(weather.dateTime)
        }
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

        other as DailyWeatherModel

        if (weather != other.weather) return false
        if (unitOfTemp != other.unitOfTemp) return false

        return true
    }
}