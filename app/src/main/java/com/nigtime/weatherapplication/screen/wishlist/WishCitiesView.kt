/*
 * Сreated by Igor Pokrovsky. 2020/4/24
 */

package com.nigtime.weatherapplication.screen.wishlist


import com.nigtime.weatherapplication.domain.city.WishCity
import com.nigtime.weatherapplication.screen.common.MvpView


interface WishCitiesView : MvpView {

    fun submitList(items: List<WishCity>)
    fun showProgressBar()
    fun showMessageEmpty()
    fun showList()
    fun insertItemToList(item: WishCity, position: Int)
    fun scrollListToPosition(position: Int)
    fun delayScrollListToPosition(position: Int)
    fun showUndoDeleteSnack(duration: Int)
    fun hideUndoDeleteSnack()
    fun showPopupMessageEmptyList()
    fun navigateToPreviousScreen()
    fun navigateToSearchCityScreen()
    fun setSelectCity(position: Int)
}