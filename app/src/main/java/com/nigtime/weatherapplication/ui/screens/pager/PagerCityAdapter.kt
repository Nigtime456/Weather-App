/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.ui.screens.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nigtime.weatherapplication.domain.city.CityForForecast
import com.nigtime.weatherapplication.ui.screens.currentforecast.CurrentForecastFragment

class PagerCityAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
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