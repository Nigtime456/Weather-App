/*
 * Сreated by Igor Pokrovsky. 2020/4/24
 */

package com.nigtime.weatherapplication.screen.savedlocations


import com.nigtime.weatherapplication.domain.location.SavedLocation


interface SavedLocationView {
    fun showProgressLayout()
    fun showEmptyLayout()
    fun showListLayout()
    fun showDialogAboutEmptyList()

    fun showUndoDeleteSnack(duration: Int)
    fun hideUndoDeleteSnack()

    fun submitList(items: List<SavedLocation>)
    fun notifyItemInserted(position: Int)
    fun scrollToPosition(position: Int)
    fun delayScrollToPosition(position: Int)
    fun setSelectionResult(position: Int)

    fun navigateToPreviousScreen()
    fun navigateToSearchCityScreen()
}