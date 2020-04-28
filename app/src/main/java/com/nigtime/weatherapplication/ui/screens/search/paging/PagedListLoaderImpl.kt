/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.search.paging

import androidx.paging.PagedList
import androidx.paging.toObservable
import com.nigtime.weatherapplication.db.data.SearchCity
import com.nigtime.weatherapplication.db.repository.PagedSearchRepository
import com.nigtime.weatherapplication.utility.rx.SchedulerProvider
import io.reactivex.Observable

/**
 * Вспомогательный класс, для ассихнронной загрузки списка городов
 */
class PagedListLoaderImpl(private val schedulerProvider: SchedulerProvider) : PagedListLoader {

    override fun loadList(pagedRepository: PagedSearchRepository, query: String): Observable<PagedList<SearchCity>> {
        return SearchCitySourceFactory(pagedRepository, query)
            .toObservable(
                PagedConfig.default(),
                fetchScheduler = schedulerProvider.syncDatabase(),
                notifyScheduler = schedulerProvider.ui()
            )
    }
}