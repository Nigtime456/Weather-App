/*
 * Сreated by Igor Pokrovsky. 2020/5/17
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/2
 */

package com.gmail.nigtime456.weatherapplication.screen.currentforecast.list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.common.util.list.BaseAdapter
import com.gmail.nigtime456.weatherapplication.common.util.list.SimpleDiffCallback
import com.gmail.nigtime456.weatherapplication.domain.forecast.HourlyForecast
import com.gmail.nigtime456.weatherapplication.domain.settings.UnitOfTemp

class HourlyWeatherAdapter :
    BaseAdapter<HourlyForecast.HourlyWeather, HourlyWeatherViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : SimpleDiffCallback<HourlyForecast.HourlyWeather>() {
            override fun areItemsTheSame(
                old: HourlyForecast.HourlyWeather,
                new: HourlyForecast.HourlyWeather
            ): Boolean {
                return old.unixTimestamp == new.unixTimestamp
            }

            override fun areContentsTheSame(
                old: HourlyForecast.HourlyWeather,
                new: HourlyForecast.HourlyWeather
            ): Boolean {
                return old == new
            }
        }
    }

    private lateinit var unitOfTemp: UnitOfTemp

    fun submitList(newList: List<HourlyForecast.HourlyWeather>, unitOfTemp: UnitOfTemp) {
        val calculateDiffs = this::unitOfTemp.isInitialized && this.unitOfTemp == unitOfTemp
        this.unitOfTemp = unitOfTemp
        submitList(newList, calculateDiffs)
    }

    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): HourlyWeatherViewHolder {
        return HourlyWeatherViewHolder(
            inflater.inflate(R.layout.item_hourly_forecast, parent, false)
        )
    }

    override fun bindViewHolder(
        holder: HourlyWeatherViewHolder,
        position: Int,
        item: HourlyForecast.HourlyWeather
    ) {
        holder.bindItem(item, unitOfTemp)
    }

}