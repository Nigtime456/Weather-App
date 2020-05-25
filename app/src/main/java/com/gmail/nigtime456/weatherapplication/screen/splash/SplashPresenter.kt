/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.gmail.nigtime456.weatherapplication.screen.splash


import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocationsRepository
import com.gmail.nigtime456.weatherapplication.screen.common.BasePresenter
import io.reactivex.rxkotlin.subscribeBy

class SplashPresenter(
    private val savedLocationsRepository: SavedLocationsRepository
) : BasePresenter<SplashView>(TAG) {

    companion object {
        private const val TAG = "splash"
    }

    override fun onAttach() {
        super.onAttach()
        dispatchScreen()
    }

    private fun dispatchScreen() {
        savedLocationsRepository.hasLocations()
            .subscribeBy(onSuccess = this::dispatchResult)
            .disposeOnDestroy()
    }

    private fun dispatchResult(hasCities: Boolean) {
        getView()?.finishSplash()
        if (hasCities) {
            getView()?.navigateToLocationPagesScreen()
        } else {
            getView()?.navigateToSavedLocationsScreen()
        }
    }
}