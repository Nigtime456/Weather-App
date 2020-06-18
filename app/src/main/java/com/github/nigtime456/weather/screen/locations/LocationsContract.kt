/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.github.nigtime456.weather.screen.locations

import com.github.nigtime456.weather.data.location.SavedLocation
import com.github.nigtime456.weather.screen.base.BasePresenter

interface LocationsContract {

    interface View {
        //ui
        fun showProgressLayout()
        fun showEmptyLayout()
        fun showListLayout()
        fun showDialogEmptyList()
        fun showMessageRemoved()
        fun showSearchScreen()
        fun showLocations(items: List<SavedLocation>)
        fun scrollTo(position: Int)

        //control
        fun setSelectResult(position: Int)
        fun finishView()
    }

    interface Presenter : BasePresenter {
        //action
        fun loadLocations()

        //data
        fun removeItem(item: SavedLocation)
        fun updateItems(items: List<SavedLocation>)

        //ui events
        fun onListUpdated()
        fun clickItem(position: Int)
        fun clickAddCity()
        fun backPressed()

        //system events
        fun onCityInserted(position: Int)
    }
}