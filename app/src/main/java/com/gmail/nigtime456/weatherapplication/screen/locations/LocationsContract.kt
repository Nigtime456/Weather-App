/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.gmail.nigtime456.weatherapplication.screen.locations

import com.gmail.nigtime456.weatherapplication.data.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.screen.base.BasePresenter

interface LocationsContract {

    interface View {
        //ui
        fun showProgressLayout()
        fun showEmptyLayout()
        fun showListLayout()
        fun showDialogEmptyList()
        fun showMessageDelete()
        fun showSearchScreen()
        fun showLocations(items: List<SavedLocation>)
        fun scrollTo(position: Int)

        //control
        fun setSelectResult(position: Int)
        fun finishView()
    }

    interface Presenter : BasePresenter<View> {
        //action
        fun loadLocations()

        //data
        fun deleteItem(item: SavedLocation)
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