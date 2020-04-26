/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.searchcity.paging

import androidx.paging.PagedList
import com.nigtime.weatherapplication.db.data.SearchCityData
import io.reactivex.Observable

/**
 * Абстракция для загрузки списка
 */
interface PagingListLoader {
    fun load(
        query: String,
        selectedCitiesIds: Set<Long>
    ): Observable<PagedList<SearchCityData>>
}