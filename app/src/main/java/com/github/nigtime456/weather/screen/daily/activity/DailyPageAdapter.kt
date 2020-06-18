/*
 * Сreated by Igor Pokrovsky. 2020/6/16
 */

package com.github.nigtime456.weather.screen.daily.activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.github.nigtime456.weather.data.forecast.DailyForecast
import com.github.nigtime456.weather.screen.daily.fragment.DailyFragment

class DailyPageAdapter constructor(host: FragmentActivity) : FragmentStateAdapter(host) {

    private var items = emptyList<DailyForecast>()

    fun submitList(newList: List<DailyForecast>) {
        items = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment {
        return DailyFragment.newInstance(items[position])
    }

    override fun getItemId(position: Int): Long {
        return items[position].timeMs
    }

    override fun containsItem(itemId: Long): Boolean {
        return items.find { forecast -> forecast.timeMs == itemId } != null
    }
}