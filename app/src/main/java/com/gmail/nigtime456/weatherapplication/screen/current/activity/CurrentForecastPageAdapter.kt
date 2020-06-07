/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */


package com.gmail.nigtime456.weatherapplication.screen.current.activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gmail.nigtime456.weatherapplication.data.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.screen.current.fragment.CurrentForecastFragment

class CurrentForecastPageAdapter(
    host: FragmentActivity,
    private var items: List<SavedLocation>
) : FragmentStateAdapter(host) {

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