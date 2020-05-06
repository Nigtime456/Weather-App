/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screens.search

import androidx.paging.PagedList
import com.nigtime.weatherapplication.domain.city.SearchCity
import com.nigtime.weatherapplication.screens.common.MvpView

interface SearchCityView : MvpView {
    fun submitList(pagedList: PagedList<SearchCity>)
    fun delayScrollListToPosition(position: Int)
    fun showHint()
    fun showProgressBar()
    fun showList()
    fun showMessageEmpty()
    fun showMessageAlreadyWish()
    fun setInsertedResultOk(position: Int)
    fun setInsertedResultCanceled()
    fun navigateToPreviousScreen()
}