/*
 * Сreated by Igor Pokrovsky. 2020/6/4
 */

package com.github.nigtime456.weather.screen.currently.fragment.list

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.nigtime456.weather.R
import com.github.nigtime456.weather.data.forecast.HourlyForecast
import com.github.nigtime456.weather.data.settings.UnitOfTemp
import com.github.nigtime456.weather.data.weather.WeatherUtils
import com.github.nigtime456.weather.utils.ui.DateFormatters
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_hourly_forecast.*
import java.text.SimpleDateFormat

class HourlyForecastModel(
    private val forecast: HourlyForecast,
    private val unitOfTemp: UnitOfTemp,
    private val timeZone: String
) : AbstractFlexibleItem<HourlyForecastModel.ViewHolder>() {

    companion object {
        const val VIEW_TYPE = R.layout.item_hourly_forecast

        fun mapList(
            list: List<HourlyForecast>,
            unitOfTemp: UnitOfTemp,
            timeZone: String
        ): List<HourlyForecastModel> {
            return list.map { forecast -> HourlyForecastModel(forecast, unitOfTemp, timeZone) }
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
        holder.item_hourly_forecast_time.text = formatTime(context)
        holder.item_hourly_forecast_temp.text =
            WeatherUtils.formatTemp(context, unitOfTemp, forecast.temp)
        holder.item_hourly_forecast_ico.setImageResource(WeatherUtils.getWeatherIcon(forecast.iconCode))
    }

    private fun formatTime(context: Context): CharSequence {
        if (!::hoursFormatter.isInitialized) {
            hoursFormatter = DateFormatters.getHoursFormatter(context, timeZone)
        }
        return hoursFormatter.format(forecast.timeMs)
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
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HourlyForecastModel

        if (forecast != other.forecast) return false
        if (unitOfTemp != other.unitOfTemp) return false

        return true
    }
}