/*
 * Сreated by Igor Pokrovsky. 2020/5/17
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.current.forecast.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gmail.nigtime456.weatherapplication.ui.util.DateFormatters
import com.gmail.nigtime456.weatherapplication.domain.forecast.HourlyWeather
import com.gmail.nigtime456.weatherapplication.domain.settings.UnitOfTemp
import com.gmail.nigtime456.weatherapplication.domain.util.UnitFormatHelper
import kotlinx.android.synthetic.main.item_hourly_forecast.view.*


class HourlyWeatherViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private val unitFormatHelper = UnitFormatHelper(itemView.context)
    private val dateFormatter = DateFormatters.getHoursFormatter(itemView.context)

    fun bindItem(weather: HourlyWeather, unitOfTemp: UnitOfTemp) {
        itemView.itemHourlyTemp.text = unitFormatHelper.formatTemp(unitOfTemp, weather.temp)
        itemView.itemHourlyWeatherIco.setImageResource(weather.ico)
        itemView.itemHourlyTime.text = dateFormatter.format(weather.dateTime)
    }
}