/*
 * Сreated by Igor Pokrovsky. 2020/5/17
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.currentforecast.list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.utility.list.BaseAdapter
import com.nigtime.weatherapplication.common.utility.list.SimpleDiffCallback
import com.nigtime.weatherapplication.domain.forecast.DailyForecast
import com.nigtime.weatherapplication.domain.settings.UnitOfTemp

class DailyWeatherAdapter constructor(private val onItemClickListener: (Int) -> Unit) :
    BaseAdapter<DailyForecast.DailyWeather, DailyWeatherViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : SimpleDiffCallback<DailyForecast.DailyWeather>() {
            override fun areItemsTheSame(
                old: DailyForecast.DailyWeather,
                new: DailyForecast.DailyWeather
            ): Boolean {
                return old.unixTimestamp == new.unixTimestamp
            }

            override fun areContentsTheSame(
                old: DailyForecast.DailyWeather,
                new: DailyForecast.DailyWeather
            ): Boolean {
                return old == new
            }

        }


    }

    private lateinit var unitOfTemp: UnitOfTemp

    fun setUnitOfTemp(unitOfTemp: UnitOfTemp) {
        this.unitOfTemp = unitOfTemp
    }

    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): DailyWeatherViewHolder {
        return DailyWeatherViewHolder(inflater.inflate(R.layout.item_daily_forecast, parent, false))
    }

    override fun bindViewHolder(
        holder: DailyWeatherViewHolder,
        position: Int,
        item: DailyForecast.DailyWeather
    ) {
        holder.bindItem(item, unitOfTemp)
    }

    override fun onViewAttachedToWindow(holder: DailyWeatherViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener {
            onItemClickListener(getItem(holder.adapterPosition).index)
        }
    }

    override fun onViewDetachedFromWindow(holder: DailyWeatherViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.setOnClickListener(null)
    }
}