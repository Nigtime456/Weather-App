/*
 * Сreated by Igor Pokrovsky. 2020/5/23
 */

package com.gmail.nigtime456.weatherapplication.screen.dailypages

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.screen.dailyforecast.DailyForecastFragment

class DailyPagesAdapter(host: Fragment, private val location: SavedLocation) :
    FragmentStateAdapter(host) {

    companion object {
        private const val DAYS_COUNT = 16
    }

    fun update() {
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = DAYS_COUNT

    override fun createFragment(position: Int): Fragment {
        Log.d("sas", "createFrag")
        return DailyForecastFragment.newInstance(location, position)
    }

}