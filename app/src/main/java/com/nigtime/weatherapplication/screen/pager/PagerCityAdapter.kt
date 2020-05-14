/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screen.pager

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nigtime.weatherapplication.common.App
import com.nigtime.weatherapplication.common.rx.RxAsyncDiffer
import com.nigtime.weatherapplication.common.utility.list.GenericCallback
import com.nigtime.weatherapplication.common.utility.list.SimpleDiffCallback
import com.nigtime.weatherapplication.domain.city.CityForForecast
import com.nigtime.weatherapplication.screen.currentforecast.CurrentForecastFragment
import kotlin.system.measureTimeMillis

class PagerCityAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    companion object {
        val DIFF_CALLBACK = object : SimpleDiffCallback<CityForForecast>() {
            override fun areItemsTheSame(old: CityForForecast, new: CityForForecast): Boolean {
                return old.cityId == new.cityId
            }

            override fun areContentsTheSame(old: CityForForecast, new: CityForForecast): Boolean {
                return old.cityId == new.cityId
            }
        }
    }

    private var items = emptyList<CityForForecast>()


    fun submitList(newList: List<CityForForecast>) {
        val callback = GenericCallback(items, newList, DIFF_CALLBACK)
        DiffUtil.calculateDiff(callback,false).apply {
            items = newList
            dispatchUpdatesTo(this@PagerCityAdapter)
        }
    }

    override fun getItemId(position: Int): Long {
        return items[position].cityId
    }

    override fun containsItem(itemId: Long): Boolean {
        return items.find { it.cityId == itemId } != null
    }

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment =
        CurrentForecastFragment.newInstance(items[position])

}