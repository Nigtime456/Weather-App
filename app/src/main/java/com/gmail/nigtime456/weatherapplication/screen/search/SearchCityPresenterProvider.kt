/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

package com.gmail.nigtime456.weatherapplication.screen.search

import com.gmail.nigtime456.weatherapplication.screen.common.BasePresenterProvider
import com.gmail.nigtime456.weatherapplication.screen.search.paging.PagedListLoader

class SearchCityPresenterProvider : BasePresenterProvider<SearchCityPresenter>() {

    override fun createPresenter(): SearchCityPresenter {
        return SearchCityPresenter(
            appContainer.getPagedSearchRepository(),
            PagedListLoader(appContainer.schedulerProvider)
        )
    }
}