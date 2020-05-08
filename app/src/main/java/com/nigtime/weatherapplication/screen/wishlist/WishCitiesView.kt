/*
 * Сreated by Igor Pokrovsky. 2020/4/24
 */

package com.nigtime.weatherapplication.screen.wishlist


import com.nigtime.weatherapplication.domain.city.WishCity
import com.nigtime.weatherapplication.screen.common.MvpView


interface WishCitiesView : MvpView {
    fun submitList(items: List<WishCity>)
    fun showProgressLayout()
    fun showEmptyLayout()
    fun showListLayout()
    fun insertItemToList(item: WishCity, position: Int)
    fun scrollToPosition(position: Int)
    fun delayScrollToPosition(position: Int)
    fun showUndoDeleteSnack(durationMillis: Int)
    fun hideUndoDeleteSnack()
    fun showDialogEmptyList()
    fun setSelectedCity(position: Int)
    fun navigateToPreviousScreen()
    fun navigateToSearchCityScreen()
}