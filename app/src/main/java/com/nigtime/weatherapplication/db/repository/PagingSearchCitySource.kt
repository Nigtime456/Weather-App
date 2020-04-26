/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.db.repository

import com.nigtime.weatherapplication.db.data.SearchCityData
import io.reactivex.Single

/**
 * Абстракция для постраничной подгрузки результатов поиска городов.
 */

interface PagingSearchCitySource {
    /**
     * Загрузить порцию данных с [startPosition] количеством [loadSize]
     */
    fun loadNextPage(startPosition: Int, loadSize: Int): Single<List<SearchCityData>>
}