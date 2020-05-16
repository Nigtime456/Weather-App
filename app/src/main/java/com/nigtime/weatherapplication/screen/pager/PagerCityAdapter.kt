/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screen.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nigtime.weatherapplication.domain.city.CityForForecast
import com.nigtime.weatherapplication.screen.currentforecast.CurrentForecastFragment

class PagerCityAdapter(fragment: Fragment, private var items: List<CityForForecast>) :
    FragmentStateAdapter(fragment) {

    fun submitList(newList: List<CityForForecast>) {
        items = newList
        notifyDataSetChanged()
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