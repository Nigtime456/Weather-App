/*
 * Сreated by Igor Pokrovsky. 2020/5/23
 */

package com.gmail.nigtime456.weatherapplication.screen.daily.host

import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation

interface DailyHostView {
    fun setLocation(title: String)
    fun setViewPager(location: SavedLocation)
    fun setTabLayout(titles: List<String>)
    fun setPage(page: Int)

    fun stopRefreshing()

    fun showErrorMessage()

    fun navigateToPreviousScreen()
}