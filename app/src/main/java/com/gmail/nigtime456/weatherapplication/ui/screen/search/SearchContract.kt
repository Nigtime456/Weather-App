/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.search

import com.gmail.nigtime456.weatherapplication.domain.location.SearchCity
import com.gmail.nigtime456.weatherapplication.ui.screen.base.BasePresenter

interface SearchContract {

    interface View {
        fun showProgressLayout()
        fun showListLayout()
        fun showEmptyLayout()
        fun showNotFoundLayout()
        fun showMessageAlreadySaved()

        fun showSearchResult(list: List<SearchCity>, scrollToPosition: Int)

        fun setInsertResult(position: Int)

        fun finishView()
    }

    interface Presenter : BasePresenter<View> {
        fun queryChanges(query: CharSequence)
        fun clickItem(city: SearchCity)

    }
}