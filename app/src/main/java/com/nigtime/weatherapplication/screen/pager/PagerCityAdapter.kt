/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screen.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nigtime.weatherapplication.domain.city.CityForForecast
import com.nigtime.weatherapplication.screen.currentforecast.CurrentForecastFragment

class PagerCityAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private var items: List<CityForForecast> = emptyList()

    fun submitList(items: List<CityForForecast>) {
        this.items = items
        //данный список не планируется обновлять между onCreateView() и onDestroyView()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment =
        CurrentForecastFragment.newInstance(items[position])


}