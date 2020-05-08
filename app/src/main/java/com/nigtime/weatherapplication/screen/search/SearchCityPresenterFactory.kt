/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.search

import com.nigtime.weatherapplication.screen.common.BasePresenterFactory
import com.nigtime.weatherapplication.screen.search.paging.PagedListLoader

class SearchCityPresenterFactory : BasePresenterFactory<SearchCityPresenter>() {
    private val presenter: SearchCityPresenter = SearchCityPresenter(
        appContainer.schedulerProvider,
        appContainer.pagedSearchRepository,
        PagedListLoader(appContainer.schedulerProvider)
    )

    override fun createPresenter(): SearchCityPresenter = presenter
}