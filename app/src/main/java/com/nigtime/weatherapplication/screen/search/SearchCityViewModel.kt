/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.search

import android.util.Log
import androidx.lifecycle.ViewModel
import com.nigtime.weatherapplication.common.App
import com.nigtime.weatherapplication.screen.common.PresenterProvider
import com.nigtime.weatherapplication.screen.search.paging.PagedListLoader

class SearchCityViewModel : ViewModel(), PresenterProvider<SearchCityPresenter> {
    private val presenter: SearchCityPresenter

    init {
        val appContainer = App.INSTANCE.appContainer
        presenter = SearchCityPresenter(
            appContainer.schedulerProvider,
            appContainer.pagedSearchRepository,
            PagedListLoader(appContainer.schedulerProvider)
        )
    }

    override fun providePresenter(): SearchCityPresenter = presenter

}