/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.searchcity

import androidx.paging.PagedList
import com.nigtime.weatherapplication.db.data.SearchCityData
import com.nigtime.weatherapplication.ui.screens.common.MvpView

interface SearchCityView : MvpView {
    fun submitList(pagedList: PagedList<SearchCityData>)
    fun showHint()
    fun showProgressBar()
    fun showList()
    fun showMessageEmpty()
    fun showMessageAlreadySelected()
    fun navigateToPreviousScreen()
}