/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screen.pages

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.domain.location.ForecastLocation
import com.nigtime.weatherapplication.screen.currentforecast.CurrentForecastFragment

class LocationPagesAdapter(host: Fragment, private var items: List<ForecastLocation>) :
    FragmentStateAdapter(host) {

    fun submitList(newList: List<ForecastLocation>) {
        items = newList
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        R.id.locationPagesDrawer
        return items[position].getKey()
    }

    override fun containsItem(itemId: Long): Boolean {
        return items.find { it.getKey() == itemId } != null
    }

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment =
        CurrentForecastFragment.newInstance(items[position])

}