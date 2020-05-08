/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.currentforecast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.helper.list.BaseAdapter
import com.nigtime.weatherapplication.domain.forecast.DailyForecast
import com.nigtime.weatherapplication.domain.settings.UnitFormatter
import kotlinx.android.synthetic.main.item_daily_forecast.view.*

class DailyWeatherAdapter constructor(
    private val unitFormatter: UnitFormatter,
    private val onClickListener: (Int) -> Unit
) : BaseAdapter<DailyForecast.DailyWeather, DailyWeatherAdapter.VH>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DailyForecast.DailyWeather>() {
            override fun areItemsTheSame(
                oldItem: DailyForecast.DailyWeather,
                newItem: DailyForecast.DailyWeather
            ): Boolean {
                return oldItem.unixTime == newItem.unixTime
            }

            override fun areContentsTheSame(
                oldItem: DailyForecast.DailyWeather,
                newItem: DailyForecast.DailyWeather
            ): Boolean {
                return oldItem.unixTime == newItem.unixTime
            }

        }
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItem(item: DailyForecast.DailyWeather, unitFormatter: UnitFormatter) {
            itemView.itemDayDTemp.text = unitFormatter.formatTemp(item.maxTemp)
            itemView.itemDayNTemp.text = unitFormatter.formatTemp(item.minTemp)
            itemView.itemDayIco.setImageResource(item.ico)
            itemView.itemDayCurrentDate.text = unitFormatter.getCompactDateByDailyWeather(item)
        }
    }

    override fun onViewAttachedToWindow(holder: VH) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener {
            onClickListener(getItem(holder.adapterPosition).index)
        }
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.setOnClickListener(null)
    }

    override fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): VH {
        return VH(inflater.inflate(R.layout.item_daily_forecast, parent, false))
    }

    override fun bindViewHolder(holder: VH, position: Int, item: DailyForecast.DailyWeather) {
        holder.bindItem(item, unitFormatter)
    }
}