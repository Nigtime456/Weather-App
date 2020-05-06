/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.screens.search.paging

import androidx.paging.PagedList
import androidx.paging.toObservable
import com.nigtime.weatherapplication.domain.city.SearchCity
import com.nigtime.weatherapplication.domain.city.PagedSearchRepository
import com.nigtime.weatherapplication.common.rx.SchedulerProvider
import io.reactivex.Observable

/**
 * Вспомогательный класс, для ассихнронной загрузки списка городов
 */
class PagedListLoaderImpl(private val schedulerProvider: SchedulerProvider) : PagedListLoader {

    override fun loadList(
        pagedRepository: PagedSearchRepository,
        query: String
    ): Observable<PagedList<SearchCity>> {
        return SearchCitySourceFactory(pagedRepository, query)
            .toObservable(
                PagedConfig.default(),
                fetchScheduler = schedulerProvider.syncDatabase(),
                notifyScheduler = schedulerProvider.ui()
            )
    }
}