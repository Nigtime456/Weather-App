/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.search

import androidx.paging.PagedList
import com.nigtime.weatherapplication.db.data.SearchCity
import com.nigtime.weatherapplication.ui.screens.common.MvpView

interface SearchCityView : MvpView {
    fun submitList(pagedList: PagedList<SearchCity>)
    fun showHint()
    fun showProgressBar()
    fun showList()
    fun showMessageEmpty()
    fun showMessageAlreadyWish()
    fun navigateToPreviousScreen()
}