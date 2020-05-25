/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.gmail.nigtime456.weatherapplication.screen.current.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.screen.current.forecast.CurrentForecastFragment

class CurrentForecastPageAdapter(host: Fragment, private var items: List<SavedLocation>) :
    FragmentStateAdapter(host) {

    fun submitList(newList: List<SavedLocation>) {
        items = newList
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return items[position].getKey()
    }

    override fun containsItem(itemId: Long): Boolean {
        return items.find { savedLocation -> savedLocation.getKey() == itemId } != null
    }

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment {
        return CurrentForecastFragment.newInstance(items[position])
    }

}