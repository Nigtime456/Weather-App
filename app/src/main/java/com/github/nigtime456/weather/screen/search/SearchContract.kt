/*
 * Сreated by Igor Pokrovsky. 2020/6/6
 */

/*
 * Сreated by Igor Pokrovsky. 2020/6/6
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.github.nigtime456.weather.screen.search

import com.github.nigtime456.weather.data.location.SearchCity
import com.github.nigtime456.weather.screen.base.BasePresenter

interface SearchContract {

    interface View {
        //ui
        fun showProgressLayout()
        fun showListLayout()
        fun showEmptyLayout()
        fun showNotFoundLayout()
        fun showMessageAlreadySaved()
        fun showSearchResult(items: List<SearchCity>)
        fun scrollToPosition(position: Int)

        //control
        fun setInsertResult(position: Int)
        fun finishView()
    }

    interface Presenter : BasePresenter {
        //ui events
        fun queryChanges(query: String)
        fun onListUpdated()
        fun clickItem(item: SearchCity)
    }
}