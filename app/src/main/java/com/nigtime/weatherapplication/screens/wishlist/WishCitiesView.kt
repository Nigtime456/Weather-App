/*
 * Сreated by Igor Pokrovsky. 2020/4/24
 */

package com.nigtime.weatherapplication.screens.wishlist


import com.nigtime.weatherapplication.domain.city.WishCity
import com.nigtime.weatherapplication.screens.common.MvpView


interface WishCitiesView : MvpView {

    fun submitList(items: List<WishCity>)
    fun showProgressBar()
    fun showMessageEmpty()
    fun showList()
    fun insertItemToList(item: WishCity, position: Int)
    fun scrollListToPosition(position: Int)
    fun delayScrollListToPosition(position: Int)
    fun showUndoDeleteSnack()
    fun hideUndoDeleteSnack()
    fun navigateToPreviousScreen()
    fun navigateToPageScreen(position: Int)
    fun showPopupMessageEmptyList()
}