/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.searchcity.paging

import androidx.paging.PagedList
import androidx.paging.toObservable
import com.nigtime.weatherapplication.db.data.SearchCityData
import com.nigtime.weatherapplication.db.repository.PagingSearchCitySourceImpl
import com.nigtime.weatherapplication.db.service.GeoCityDao
import com.nigtime.weatherapplication.utility.rx.SchedulerProvider
import io.reactivex.Observable

/**
 * Вспомогательный класс, для ассихнронной загрузки списка городов
 */
class PagingListLoaderImpl constructor(
    private val geoCityDao: GeoCityDao,
    private val schedulerProvider: SchedulerProvider
) : PagingListLoader {

    override fun load(
        query: String,
        selectedCitiesIds: Set<Long>
    ): Observable<PagedList<SearchCityData>> {
        val searchCitySource =
            PagingSearchCitySourceImpl(geoCityDao, selectedCitiesIds, query.toLowerCase())
        return SearchCityDataSourceFactory(searchCitySource)
            .toObservable(
                PagingConfig.default(),
                fetchScheduler = schedulerProvider.io(),
                notifyScheduler = schedulerProvider.ui()
            )
    }
}