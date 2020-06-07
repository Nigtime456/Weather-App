/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.gmail.nigtime456.weatherapplication.screen.current.activity

import android.os.Parcelable
import com.gmail.nigtime456.weatherapplication.data.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.screen.base.BasePresenter
import kotlinx.android.parcel.Parcelize

interface CurrentForecastActivityContract {

    interface View {
        //ui
        fun showPages(items: List<SavedLocation>)
        fun showNavView(items: List<SavedLocation>)
        fun setCurrentPage(page: Int)
        fun setCurrentNavItem(index: Int)
        fun showDialogAboutApp()
        fun showLocationsScreen()
        fun showSettingsScreen()
        fun showNotificationsScreen()
    }

    interface Presenter : BasePresenter<View> {

        fun restoreState(state: State?)
        fun getState(): State

        fun loadLocations()
        fun scrollPage(page: Int)
        fun clickNavItem(index: Int)
        fun clickAboutApp()
        fun clickEditLocations()
        fun clickSettings()
        fun clickWeatherNotifications()

        fun onCityInserted(position: Int)
        fun onCitySelected(position: Int)
    }

    @Parcelize
    class State(val page: Int) : Parcelable
}