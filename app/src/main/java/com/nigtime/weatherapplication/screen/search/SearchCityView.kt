/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.search

import androidx.paging.PagedList
import com.nigtime.weatherapplication.domain.location.SearchCity

interface SearchCityView {
    fun showHintLayout()
    fun showProgressLayout()
    fun showListLayout()
    fun showEmptyLayout()
    fun showToastAlreadyAdded()

    fun hideSoftKeyboard()

    fun submitList(pagedList: PagedList<SearchCity>)
    fun delayScrollToPosition(position: Int)

    fun setInsertionResult(position: Int)

    fun navigateToPreviousScreen()
}