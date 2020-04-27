/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.splash

import com.nigtime.weatherapplication.db.source.SelectedCitySource
import com.nigtime.weatherapplication.ui.screens.common.BasePresenter
import com.nigtime.weatherapplication.utility.rx.SchedulerProvider

class SplashPresenter(
    schedulerProvider: SchedulerProvider,
    private val selectedCitySource: SelectedCitySource
) :
    BasePresenter<SplashView>(schedulerProvider, TAG) {

    companion object {
        private const val TAG = "splash"
    }

    fun dispatchScreen() {
        getView()?.playAnimation()
        selectedCitySource.hasCities()
            .subscribeOn(schedulerProvider.syncDatabase())
            .observeOn(schedulerProvider.ui())
            .subscribe(this::dispatchResult, this::rethrowError)
            .disposeOnDetach()
    }

    private fun dispatchResult(hasSelectedCities: Boolean) {
        if (hasSelectedCities) {
            getView()?.delayedLoadPagerScreen()
        } else {
            getView()?.delayedLoadSearchCityScreen()
        }
    }
}