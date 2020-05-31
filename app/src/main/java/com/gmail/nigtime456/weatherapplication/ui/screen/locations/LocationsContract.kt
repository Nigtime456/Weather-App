/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.locations

import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.ui.screen.base.BasePresenter

interface LocationsContract {

    interface View {
        fun showProgressLayout()
        fun showEmptyLayout()
        fun showListLayout()
        fun showMessageAboutEmptyList()

        fun showUndoDeleteSnack(duration: Int)
        fun hideUndoDeleteSnack()

        fun showLocations(items: List<SavedLocation>, scrollToPosition: Int)
        fun showLocations(items: List<SavedLocation>)

        fun notifyItemInserted(position: Int)
        fun scrollToPosition(position: Int)

        fun setSelectResult(position: Int)

        fun showSearchScreen()

        fun finishView()
    }

    interface Presenter :
        BasePresenter<View> {
        fun loadLocations()
        fun swipeItem(swiped: SavedLocation, position: Int)
        fun moveItems(
            moved: SavedLocation,
            movedPosition: Int,
            target: SavedLocation,
            targetPosition: Int
        )

        fun completeMovement()
        fun clickItem(position: Int)
        fun clickUndoDelete()
        fun clickAddCity()
        fun backPressed()

        fun onCityInserted(position: Int)
    }
}