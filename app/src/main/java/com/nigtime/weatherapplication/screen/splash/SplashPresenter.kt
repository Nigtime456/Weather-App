/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.splash


import com.nigtime.weatherapplication.common.rx.SchedulerProvider
import com.nigtime.weatherapplication.domain.city.WishCitiesRepository
import com.nigtime.weatherapplication.screen.common.BasePresenter

class SplashPresenter(
    schedulerProvider: SchedulerProvider,
    private val wishCitiesRepository: WishCitiesRepository
) :
    BasePresenter<SplashView>(schedulerProvider, TAG) {

    companion object {
        private const val TAG = "splash"
    }

    override fun onAttach() {
        super.onAttach()
        dispatchScreen()
    }

    private fun dispatchScreen() {
        wishCitiesRepository.hasCities()
            .subscribeOn(schedulerProvider.syncDatabase())
            .observeOn(schedulerProvider.ui())
            .subscribeAndHandleError { hasCities ->
                dispatchResult(hasCities)
            }
    }

    private fun dispatchResult(hasCities: Boolean) {
        getView()?.finishSplash()
        if (hasCities) {
            getView()?.navigateToPagerScreen()
        } else {
            getView()?.navigateToWishListScreen()
        }
    }
}