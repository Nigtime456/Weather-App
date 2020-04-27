/*
 * Сreated by Igor Pokrovsky. 2020/4/24
 */

package com.nigtime.weatherapplication.ui.screens.listcities


import com.nigtime.weatherapplication.db.data.SelectedCityData
import com.nigtime.weatherapplication.ui.screens.common.MvpView


interface ListCitiesView : MvpView {

    fun submitList(list: List<SelectedCityData>)
    fun showProgressBar()
    fun showMessageEmpty()
    fun showList()
    fun insertItemToList(item: SelectedCityData, position: Int)
    fun showUndoDeleteSnack()
    fun hideUndoDeleteSnack()
    fun navigateToPreviousScreen()
    fun navigateToPage(position: Int)
}