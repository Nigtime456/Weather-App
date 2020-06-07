/*
 * Сreated by Igor Pokrovsky. 2020/6/6
 */

/*
 * Сreated by Igor Pokrovsky. 2020/6/6
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.gmail.nigtime456.weatherapplication.screen.search

import com.gmail.nigtime456.weatherapplication.data.location.SearchCity
import com.gmail.nigtime456.weatherapplication.screen.base.BasePresenter

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

    interface Presenter : BasePresenter<View> {
        //ui events
        fun queryChanges(query: CharSequence)
        fun onListUpdated()
        fun clickItem(item: SearchCity)
    }
}