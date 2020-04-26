/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.searchcity.paging

import androidx.annotation.WorkerThread
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource
import com.nigtime.weatherapplication.db.data.SearchCityData
import com.nigtime.weatherapplication.db.repository.PagingSearchCitySource

/**
 * Фабрика [DataSource], нужна для подрузки первой партии данных асинхронно
 *
 */
class SearchCityDataSourceFactory constructor(private val searchCitySource: PagingSearchCitySource) :
    DataSource.Factory<Int, SearchCityData>() {

    override fun create(): DataSource<Int, SearchCityData> {
        return QueryCityDataSource(searchCitySource)
    }

    private class QueryCityDataSource constructor(private val searchCitySource: PagingSearchCitySource) :
        PositionalDataSource<SearchCityData>() {

        @WorkerThread
        override fun loadRange(
            params: LoadRangeParams,
            callback: LoadRangeCallback<SearchCityData>
        ) {
            //worker thread
            val result = searchCitySource.loadNextPage(params.startPosition, params.loadSize)
                .blockingGet()
            callback.onResult(result)
        }

        @WorkerThread
        override fun loadInitial(
            params: LoadInitialParams, callback: LoadInitialCallback<SearchCityData>
        ) {
            //worker thread
            val result = searchCitySource.loadNextPage(params.requestedStartPosition, params.pageSize)
                .blockingGet()
            callback.onResult(result, params.requestedStartPosition)
        }
    }
}

