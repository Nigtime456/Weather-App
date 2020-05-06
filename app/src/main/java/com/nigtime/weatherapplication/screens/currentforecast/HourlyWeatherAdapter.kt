/*
 * Сreated by Igor Pokrovsky. 2020/5/2
 */

package com.nigtime.weatherapplication.screens.currentforecast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.domain.forecast.HourlyForecast
import com.nigtime.weatherapplication.common.helper.list.BaseAdapter
import kotlinx.android.synthetic.main.item_hour_forecast.view.*

class HourlyWeatherAdapter :
    BaseAdapter<HourlyForecast.HourlyWeather, HourlyWeatherAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(weather: HourlyForecast.HourlyWeather) {
            itemView.itemHourTemp.text = weather.temp.toString()
            itemView.itemHourWeatherIco.setImageResource(weather.ico)
            itemView.itemHourTime.text = weather.hour
        }
    }

    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_hour_forecast, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }


}