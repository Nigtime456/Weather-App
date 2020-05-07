/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.search

import androidx.paging.PagedList
import com.nigtime.weatherapplication.domain.city.SearchCity
import com.nigtime.weatherapplication.screen.common.MvpView

interface SearchCityView : MvpView {
    fun submitList(pagedList: PagedList<SearchCity>)
    fun delayScrollListToPosition(position: Int)
    fun showHint()
    fun showProgressBar()
    fun showList()
    fun showMessageEmpty()
    fun showMessageAlreadyWish()
    fun setInsertedResult(position: Int)
    fun navigateToPreviousScreen()
}