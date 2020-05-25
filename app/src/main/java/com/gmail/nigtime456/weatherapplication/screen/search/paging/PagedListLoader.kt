/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.gmail.nigtime456.weatherapplication.screen.search.paging

import androidx.paging.PagedList
import androidx.paging.toFlowable
import com.gmail.nigtime456.weatherapplication.common.rx.SchedulerProvider
import com.gmail.nigtime456.weatherapplication.domain.location.PagedSearchRepository
import com.gmail.nigtime456.weatherapplication.domain.location.SearchCity
import io.reactivex.Flowable

/**
 * Вспомогательный класс, для ассихнронной загрузки списка городов
 */
class PagedListLoader(private val schedulerProvider: SchedulerProvider) {

    fun loadList(
        pagedRepository: PagedSearchRepository,
        query: String
    ): Flowable<PagedList<SearchCity>> {
        return SearchCitySourceFactory(pagedRepository, query)
            .toFlowable(
                PagingConfig().getDefault(),
                notifyScheduler = schedulerProvider.ui()
            )
    }
}