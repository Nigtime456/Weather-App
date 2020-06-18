/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.github.nigtime456.weather.screen.currently.activity

import android.os.Parcelable
import com.github.nigtime456.weather.data.location.SavedLocation
import com.github.nigtime456.weather.screen.base.BasePresenter
import kotlinx.android.parcel.Parcelize

interface CurrentlyActivityContract {

    interface View {
        //ui
        fun showLocations(items: List<SavedLocation>)
        fun showNavItems(items: List<SavedLocation>)

        fun setCurrentPage(page: Int)
        fun setCurrentNavItem(index: Int)

        fun showAboutAppScreen()

        fun showLocationsScreen()
        fun showSettingsScreen()
        fun showSearchScreen()

        fun openDrawer()
        fun closeDrawer()
    }

    interface Presenter : BasePresenter {

        fun restoreState(state: State?)
        fun getState(): State

        fun loadLocations()
        fun scrollPage(page: Int)
        fun clickNavItem(index: Int)
        fun clickNavigationButton()
        fun clickAboutApp()
        fun clickEditLocations()
        fun clickSettings()
        fun clickAddCity()

        fun onCityInserted(position: Int)
        fun onCitySelected(position: Int)
    }

    @Parcelize
    class State(val page: Int) : Parcelable
}