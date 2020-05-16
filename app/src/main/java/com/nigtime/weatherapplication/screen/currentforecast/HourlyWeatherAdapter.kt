/*
 * Сreated by Igor Pokrovsky. 2020/5/2
 */

package com.nigtime.weatherapplication.screen.currentforecast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.utility.list.BaseAdapter
import com.nigtime.weatherapplication.common.utility.list.SimpleDiffCallback
import com.nigtime.weatherapplication.domain.forecast.HourlyForecast
import com.nigtime.weatherapplication.domain.settings.UnitFormatter
import kotlinx.android.synthetic.main.item_hourly_forecast.view.*

class HourlyWeatherAdapter :
    BaseAdapter<HourlyForecast.HourlyWeather, HourlyWeatherAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : SimpleDiffCallback<HourlyForecast.HourlyWeather>() {
            override fun areItemsTheSame(
                old: HourlyForecast.HourlyWeather,
                new: HourlyForecast.HourlyWeather
            ): Boolean {
                return old.hour == new.hour
            }

            override fun areContentsTheSame(
                old: HourlyForecast.HourlyWeather,
                new: HourlyForecast.HourlyWeather
            ): Boolean {
                return old == new
            }
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(weather: HourlyForecast.HourlyWeather, unitFormatter: UnitFormatter) {
            itemView.itemHourTemp.text = unitFormatter.formatTemp(weather.temp)
            itemView.itemHourWeatherIco.setImageResource(weather.ico)
            itemView.itemHourTime.text = weather.hour
        }
    }

    private lateinit var unitFormatter: UnitFormatter

    fun setUnitFormatter(unitFormatter: UnitFormatter) {
        this.unitFormatter = unitFormatter
    }

    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_hourly_forecast, parent, false))
    }

    override fun bindViewHolder(
        holder: ViewHolder,
        position: Int,
        item: HourlyForecast.HourlyWeather
    ) {
        holder.bindItem(item, unitFormatter)
    }

}