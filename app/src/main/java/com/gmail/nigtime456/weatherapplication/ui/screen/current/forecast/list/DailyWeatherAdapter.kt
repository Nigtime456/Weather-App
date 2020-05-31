/*
 * Сreated by Igor Pokrovsky. 2020/5/17
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.current.forecast.list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.domain.forecast.DailyWeather
import com.gmail.nigtime456.weatherapplication.domain.settings.UnitOfTemp
import com.gmail.nigtime456.weatherapplication.tools.rx.RxAsyncDiffer
import com.gmail.nigtime456.weatherapplication.ui.list.BaseAdapter
import com.gmail.nigtime456.weatherapplication.ui.list.SimpleDiffCallback

class DailyWeatherAdapter constructor(
    private val onItemClickListener: (Int) -> Unit,
    rxAsyncDiffer: RxAsyncDiffer
) :
    BaseAdapter<DailyWeather, DailyWeatherViewHolder>(DIFF_CALLBACK, rxAsyncDiffer) {

    companion object {
        private val DIFF_CALLBACK = object : SimpleDiffCallback<DailyWeather>() {

            override fun areItemsTheSame(
                old: DailyWeather,
                new: DailyWeather
            ): Boolean {
                return old.dateTime == new.dateTime
            }

            override fun areContentsTheSame(
                old: DailyWeather,
                new: DailyWeather
            ): Boolean {
                return old == new
            }

        }
    }

    private lateinit var unitOfTemp: UnitOfTemp
    private var weatherList: List<DailyWeather> = emptyList()

    fun submitList(newList: List<DailyWeather>, unitOfTemp: UnitOfTemp) {
        weatherList = newList
        val calculateDiffs = this::unitOfTemp.isInitialized && this.unitOfTemp == unitOfTemp
        this.unitOfTemp = unitOfTemp
        submitList(newList, calculateDiffs)
    }

    fun setDisplayedCount(count: Int) {
        submitList(weatherList.take(count), true)
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
        item: DailyWeather
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