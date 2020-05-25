/*
 * Сreated by Igor Pokrovsky. 2020/5/17
 */

package com.gmail.nigtime456.weatherapplication.screen.currentforecast.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gmail.nigtime456.weatherapplication.common.util.DateFormatFactory
import com.gmail.nigtime456.weatherapplication.domain.forecast.HourlyForecast
import com.gmail.nigtime456.weatherapplication.domain.settings.UnitOfTemp
import com.gmail.nigtime456.weatherapplication.domain.util.UnitFormatHelper
import kotlinx.android.synthetic.main.item_hourly_forecast.view.*


class HourlyWeatherViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private val unitFormatHelper = UnitFormatHelper(itemView.context)
    private val dateFormatter = DateFormatFactory.getHoursFormatter(itemView.context)

    fun bindItem(weather: HourlyForecast.HourlyWeather, unitOfTemp: UnitOfTemp) {
        itemView.itemHourlyTemp.text = unitFormatHelper.formatTemp(unitOfTemp, weather.temp)
        itemView.itemHourlyWeatherIco.setImageResource(weather.ico)
        itemView.itemHourlyTime.text = dateFormatter.format(weather.unixTimestamp)
    }
}