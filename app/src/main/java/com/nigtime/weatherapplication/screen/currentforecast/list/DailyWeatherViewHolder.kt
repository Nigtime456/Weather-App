/*
 * Сreated by Igor Pokrovsky. 2020/5/17
 */

package com.nigtime.weatherapplication.screen.currentforecast.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.util.DateFormatFactory
import com.nigtime.weatherapplication.domain.forecast.DailyForecast
import com.nigtime.weatherapplication.domain.settings.UnitOfTemp
import com.nigtime.weatherapplication.domain.util.UnitFormatHelper
import kotlinx.android.synthetic.main.item_daily_forecast.view.*

class DailyWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val weekDayFormatter = DateFormatFactory.getWeekdayFormatter()
    private val dayOfMonthFormatter = DateFormatFactory.getDayOfMonthFormatter()
    private val unitFormatHelper = UnitFormatHelper(itemView.context)

    fun bindItem(item: DailyForecast.DailyWeather, unitOfTemp: UnitOfTemp) {
        itemView.itemDailyDTemp.text = unitFormatHelper.formatTemp(unitOfTemp, item.maxTemp)
        itemView.itemDailyNTemp.text = unitFormatHelper.formatTemp(unitOfTemp, item.minTemp)
        itemView.itemDailyIco.setImageResource(item.ico)
        itemView.itemDailyCurrentDate.text = getCompactDateByDailyWeather(item)
    }

    private fun getCompactDateByDailyWeather(dailyWeather: DailyForecast.DailyWeather): String {
        return when (dailyWeather.index) {
            0 -> itemView.context.getString(R.string.units_today)
            1 -> itemView.context.getString(R.string.units_tomorrow)
            in 2..4 -> weekDayFormatter.format(dailyWeather.unixTimestamp)
            else -> dayOfMonthFormatter.format(dailyWeather.unixTimestamp)
        }
    }
}
