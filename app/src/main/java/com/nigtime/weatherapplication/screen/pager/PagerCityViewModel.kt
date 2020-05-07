/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.pager

import androidx.lifecycle.ViewModel
import com.nigtime.weatherapplication.common.App
import com.nigtime.weatherapplication.screen.common.PresenterProvider

class PagerCityViewModel : ViewModel(), PresenterProvider<PagerCityPresenter> {
    private val presenter: PagerCityPresenter

    init {
        val appContainer = App.INSTANCE.appContainer
        presenter = PagerCityPresenter(
            appContainer.schedulerProvider,
            appContainer.forecastCitiesRepository
        )
    }

    override fun providePresenter(): PagerCityPresenter = presenter
}