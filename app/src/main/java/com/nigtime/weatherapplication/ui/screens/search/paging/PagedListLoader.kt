/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.search.paging

import androidx.paging.PagedList
import com.nigtime.weatherapplication.domain.city.SearchCity
import com.nigtime.weatherapplication.domain.repository.PagedSearchRepository
import io.reactivex.Observable

/**
 * Абстракция для загрузки списка
 */
interface PagedListLoader {
    fun loadList(
        pagedRepository: PagedSearchRepository,
        query: String
    ): Observable<PagedList<SearchCity>>
}