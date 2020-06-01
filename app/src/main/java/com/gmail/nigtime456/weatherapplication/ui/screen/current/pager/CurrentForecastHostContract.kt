/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.current.pager

import android.os.Parcelable
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.ui.screen.base.BasePresenter
import io.reactivex.subjects.Subject

interface CurrentForecastHostContract {

    interface State : Parcelable {
        fun getCurrentPage(): Int
        fun getVerticalScroll(): Int
    }

    interface View {
        fun showPages(items: List<SavedLocation>)
        fun showNavView(items: List<SavedLocation>)

        fun setCurrentPage(page: Int)
        fun setCurrentNavItem(index: Int)

        fun showDialogAboutApp()
        fun showEditLocationsScreen()
        fun showSettingsScreen()
        fun showWeatherNotificationScreen()
    }

    interface Presenter : BasePresenter<View> {

        fun restoreState(state: State?)
        fun getState(): State

        fun getSyncPageScrollSubject(): Subject<Int>

        fun loadLocations()
        fun scrollPage(page: Int)
        fun clickNavItem(index: Int)
        fun clickAboutApp()
        fun clickChangeLocations()
        fun clickSettings()
        fun clickWeatherNotifications()

        fun onCityInserted(position: Int)
        fun onCitySelected(position: Int)

    }
}