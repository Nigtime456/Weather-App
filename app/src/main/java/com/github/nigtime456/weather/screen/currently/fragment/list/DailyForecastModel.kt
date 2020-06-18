/*
 * Сreated by Igor Pokrovsky. 2020/6/5
 */

package com.github.nigtime456.weather.screen.currently.fragment.list

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.nigtime456.weather.R
import com.github.nigtime456.weather.data.forecast.DailyForecast
import com.github.nigtime456.weather.data.settings.UnitOfTemp
import com.github.nigtime456.weather.data.weather.WeatherUtils
import com.github.nigtime456.weather.utils.ui.DateFormatters
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_daily_forecast.*
import java.util.*

class DailyForecastModel(
    private val forecast: DailyForecast,
    private val unitOfTemp: UnitOfTemp
) : AbstractFlexibleItem<DailyForecastModel.ViewHolder>() {

    private val weekDayFormatter = DateFormatters.getWeekdayFormatter()

    companion object {
        const val VIEW_TYPE = R.layout.item_daily_forecast

        fun mapList(
            list: List<DailyForecast>,
            unitOfTemp: UnitOfTemp
        ): List<DailyForecastModel> {
            return list.map { forecast -> DailyForecastModel(forecast, unitOfTemp) }
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

        holder.item_daily_forecast_time.text = formatDate(context)
        holder.item_daily_forecast_high_temp.text =
            WeatherUtils.formatTemp(context, unitOfTemp, forecast.tempHigh)
        holder.item_daily_forecast_low_temp.text =
            WeatherUtils.formatTemp(context, unitOfTemp, forecast.tempLow)

        holder.item_daily_forecast_ico.setImageResource(WeatherUtils.getWeatherIcon(forecast.weatherCode))
    }

    private fun formatDate(context: Context): String {
        return when (forecast.dayIndex) {
            0 -> context.getString(R.string.fragment_currently_today)
            1 -> context.getString(R.string.fragment_currently_tomorrow)
            else -> weekDayFormatter.format(forecast.timeMs).toLowerCase(Locale.getDefault())
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
        var result = forecast.hashCode()
        result = 31 * result + unitOfTemp.hashCode()
        result = 31 * result + weekDayFormatter.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DailyForecastModel

        if (forecast != other.forecast) return false
        if (unitOfTemp != other.unitOfTemp) return false
        if (weekDayFormatter != other.weekDayFormatter) return false

        return true
    }
}