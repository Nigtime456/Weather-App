/*
 * Сreated by Igor Pokrovsky. 2020/5/17
 */

package com.gmail.nigtime456.weatherapplication.screen.current.forecast.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.common.util.DateFormatFactory
import com.gmail.nigtime456.weatherapplication.domain.forecast.DailyWeather
import com.gmail.nigtime456.weatherapplication.domain.settings.UnitOfTemp
import com.gmail.nigtime456.weatherapplication.domain.util.UnitFormatHelper
import kotlinx.android.synthetic.main.item_daily_forecast.view.*

class DailyWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val weekDayFormatter = DateFormatFactory.getWeekdayFormatter()
    private val dayOfMonthFormatter = DateFormatFactory.getDayOfMonthFormatter()
    private val unitFormatHelper = UnitFormatHelper(itemView.context)

    fun bindItem(item: DailyWeather, unitOfTemp: UnitOfTemp) {
        itemView.itemDailyDTemp.text = unitFormatHelper.formatTemp(unitOfTemp, item.maxTemp)
        itemView.itemDailyNTemp.text = unitFormatHelper.formatTemp(unitOfTemp, item.minTemp)
        itemView.itemDailyIco.setImageResource(item.ico)
        itemView.itemDailyCurrentDate.text = getCompactDateByDailyWeather(item)
    }

    private fun getCompactDateByDailyWeather(dailyWeather: DailyWeather): String {
        return when (dailyWeather.index) {
            0 -> itemView.context.getString(R.string.units_today)
            1 -> itemView.context.getString(R.string.units_tomorrow)
            in 2..4 -> weekDayFormatter.format(dailyWeather.dateTime)
            else -> dayOfMonthFormatter.format(dailyWeather.dateTime)
        }
    }
}
