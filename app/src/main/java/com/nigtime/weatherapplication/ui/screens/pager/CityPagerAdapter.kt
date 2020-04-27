/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.ui.screens.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nigtime.weatherapplication.db.data.CityForForecastData
import com.nigtime.weatherapplication.ui.screens.currentforecast.CurrentForecastFragment

class CityPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    private var items: List<CityForForecastData> = emptyList()

    fun submitList(items: List<CityForForecastData>) {
        this.items = items
        //данный список не планируется обновлять между onCreateView() и onDestroyView()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment =
        CurrentForecastFragment.newInstance(items[position])
}