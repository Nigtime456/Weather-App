/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */


package com.github.nigtime456.weather.screen.currently.activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.github.nigtime456.weather.data.location.SavedLocation
import com.github.nigtime456.weather.screen.currently.fragment.CurrentlyFragment

class CurrentlyPageAdapter(
    host: FragmentActivity,
    private var items: List<SavedLocation>
) : FragmentStateAdapter(host) {

    fun submitList(newList: List<SavedLocation>) {
        items = newList
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): SavedLocation = items[position]

    override fun getItemId(position: Int): Long {
        return items[position].id
    }

    override fun containsItem(itemId: Long): Boolean {
        return items.find { location -> location.id == itemId } != null
    }

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment {
        return CurrentlyFragment.newInstance(getItem(position))
    }
}