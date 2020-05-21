/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screen.search.paging

import androidx.annotation.WorkerThread
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import com.nigtime.weatherapplication.domain.location.PagedSearchRepository
import com.nigtime.weatherapplication.domain.location.SearchCity

/**
 * Фабрика [DataSource], нужна для подрузки первой партии данных асинхронно
 *
 */
class SearchCitySourceFactory constructor(
    private val pagedRepository: PagedSearchRepository,
    private val query: String
) :
    DataSource.Factory<Int, SearchCity>() {

    override fun create(): DataSource<Int, SearchCity> {
        return SearchDataSource(pagedRepository, query)
    }

    private class SearchDataSource constructor(
        private val pagedRepository: PagedSearchRepository,
        private val query: String
    ) :
        PositionalDataSource<SearchCity>() {

        @WorkerThread
        override fun loadRange(
            params: LoadRangeParams,
            callback: LoadRangeCallback<SearchCity>
        ) {
            //worker thread
            val result = pagedRepository.loadPage(query, params.startPosition, params.loadSize)
                .blockingGet()
            callback.onResult(result)
        }

        @WorkerThread
        override fun loadInitial(
            params: LoadInitialParams, callback: LoadInitialCallback<SearchCity>
        ) {

            //worker thread
            val result =
                pagedRepository.loadPage(query, params.requestedStartPosition, params.pageSize)
                    .blockingGet()
            callback.onResult(result, params.requestedStartPosition)
        }
    }
}

