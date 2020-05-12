/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.search

import androidx.paging.PagedList
import com.nigtime.weatherapplication.domain.city.SearchCity
import com.nigtime.weatherapplication.screen.common.MvpView

interface SearchCityView : MvpView {
    fun submitList(pagedList: PagedList<SearchCity>)

    fun delayScrollToPosition(position: Int)

    fun showHintLayout()
    fun showProgressLayout()
    fun showListLayout()
    fun showEmptyLayout()

    fun showToastAlreadyAdded()

    fun setInsertedResult(position: Int)

    fun navigateToPreviousScreen()
}