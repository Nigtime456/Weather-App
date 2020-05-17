/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.nigtime.weatherapplication.screen.search

import com.nigtime.weatherapplication.screen.common.BasePresenterProvider
import com.nigtime.weatherapplication.screen.search.paging.PagedListLoader

class SearchCityPresenterProvider : BasePresenterProvider<SearchCityPresenter>() {

    override fun createPresenter(): SearchCityPresenter {
        return SearchCityPresenter(
            appContainer.schedulerProvider,
            appContainer.getPagedSearchRepository(),
            PagedListLoader(appContainer.schedulerProvider)
        )
    }
}