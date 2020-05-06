/*
 * Сreated by Igor Pokrovsky. 2020/5/6
 */

package com.nigtime.weatherapplication.common.di

import android.content.Context
import com.nigtime.weatherapplication.screens.search.SearchCityPresenter
import com.nigtime.weatherapplication.screens.search.paging.PagedListLoaderImpl

class SearchCityContainer constructor(context: Context, appContainer: AppContainer) {
    val searchCityPresenter: SearchCityPresenter

    init {
        searchCityPresenter = SearchCityPresenter(
            appContainer.schedulerProvider, appContainer.pagedSearchRepository,
            PagedListLoaderImpl(appContainer.schedulerProvider)
        )
    }
}