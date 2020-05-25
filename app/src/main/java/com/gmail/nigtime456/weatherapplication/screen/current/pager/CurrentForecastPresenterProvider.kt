/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.gmail.nigtime456.weatherapplication.screen.current.pager

import com.gmail.nigtime456.weatherapplication.screen.common.BasePresenterProvider

class CurrentForecastPresenterProvider : BasePresenterProvider<CurrentForecastPagerPresenter>() {

    override fun createPresenter(): CurrentForecastPagerPresenter {
        return CurrentForecastPagerPresenter(appContainer.savedLocationsRepository)
    }
}