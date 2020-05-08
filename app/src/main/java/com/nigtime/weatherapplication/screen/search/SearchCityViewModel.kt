/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.search

import com.nigtime.weatherapplication.screen.common.BaseViewModel
import com.nigtime.weatherapplication.screen.common.PresenterProvider
import com.nigtime.weatherapplication.screen.search.paging.PagedListLoader

class SearchCityViewModel : BaseViewModel(), PresenterProvider<SearchCityPresenter> {
    private val presenter: SearchCityPresenter = SearchCityPresenter(
        appContainer.schedulerProvider,
        appContainer.pagedSearchRepository,
        PagedListLoader(appContainer.schedulerProvider)
    )

    override fun providePresenter(): SearchCityPresenter = presenter

}