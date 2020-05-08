/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screen.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nigtime.weatherapplication.domain.city.CityForForecast
import com.nigtime.weatherapplication.screen.currentforecast.CurrentForecastFragment

class PagerCityAdapter(fragment: Fragment, private val items: List<CityForForecast>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment =
        CurrentForecastFragment.newInstance(items[position])


}