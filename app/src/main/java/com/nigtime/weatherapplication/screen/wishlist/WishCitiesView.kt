/*
 * Сreated by Igor Pokrovsky. 2020/4/24
 */

package com.nigtime.weatherapplication.screen.wishlist


import com.nigtime.weatherapplication.domain.city.WishCity


interface WishCitiesView {
    fun submitList(items: List<WishCity>)

    fun showProgressLayout()
    fun showEmptyLayout()
    fun showListLayout()

    fun notifyItemInserted(position: Int)
    fun scrollToPosition(position: Int)
    fun delayScrollToPosition(position: Int)

    fun showUndoDeleteSnack(duration: Int)
    fun hideUndoDeleteSnack()

    fun showDialogAboutEmptyList()

    fun setSelectionResult(position: Int)

    fun navigateToPreviousScreen()
    fun navigateToSearchCityScreen()
}