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
import com.nigtime.weatherapplication.domain.forecast.HourlyForecast
import com.nigtime.weatherapplication.domain.settings.UnitFormatter
import kotlinx.android.synthetic.main.item_hourly_forecast.view.*

class HourlyWeatherAdapter constructor(private val unitFormatter: UnitFormatter) :
    BaseAdapter<HourlyForecast.HourlyWeather, HourlyWeatherAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(weather: HourlyForecast.HourlyWeather, unitFormatter: UnitFormatter) {
            itemView.itemHourTemp.text = unitFormatter.formatTemp(weather.temp)
            itemView.itemHourWeatherIco.setImageResource(weather.ico)
            itemView.itemHourTime.text = weather.hour
        }
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