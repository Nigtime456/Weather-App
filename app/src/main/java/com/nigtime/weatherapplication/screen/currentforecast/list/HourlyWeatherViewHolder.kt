/*
 * Сreated by Igor Pokrovsky. 2020/5/17
 */

package com.nigtime.weatherapplication.screen.currentforecast.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nigtime.weatherapplication.common.App
import com.nigtime.weatherapplication.common.utility.DateFormatUtils
import com.nigtime.weatherapplication.domain.forecast.HourlyForecast
import com.nigtime.weatherapplication.domain.settings.UnitOfTemp
import com.nigtime.weatherapplication.domain.utility.UnitFormatHelper
import kotlinx.android.synthetic.main.item_hourly_forecast.view.*


class HourlyWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        private val dateFormatter = DateFormatUtils.getHoursFormatter(App.INSTANCE)
    }

    private val unitFormatHelper = UnitFormatHelper(itemView.context)

    fun bindItem(weather: HourlyForecast.HourlyWeather, unitOfTemp: UnitOfTemp) {
        itemView.itemHourlyTemp.text = unitFormatHelper.formatTemp(unitOfTemp, weather.temp)
        itemView.itemHourlyWeatherIco.setImageResource(weather.ico)
        itemView.itemHourlyTime.text = dateFormatter.format(weather.unixTimestamp)
    }
}