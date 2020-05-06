/*
 * Сreated by Igor Pokrovsky. 2020/5/6
 */

package com.nigtime.weatherapplication.common.di

import android.content.Context
import com.nigtime.weatherapplication.screens.pager.PagerCityPresenter

class PagerCityContainer constructor(context: Context, appContainer: AppContainer) {
    val pagerCityPresenter: PagerCityPresenter

    init {
        pagerCityPresenter = PagerCityPresenter(
            appContainer.schedulerProvider,
            appContainer.forecastCitiesRepository
        )
    }
}