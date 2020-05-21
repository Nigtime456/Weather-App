/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.nigtime.weatherapplication.domain.location

import io.reactivex.Single

/**
 * Репозиторий для постраничной загрузки поиска.
 */
interface PagedSearchRepository {

    /**
     * @return int - позиция вставленного города.
     */
    fun insert(searchCity: SearchCity): Single<Int>

    /**
     * Загрузить результат [query] с [position] количеством [count]
     */
    fun loadPage(query: String, position: Int, count: Int): Single<List<SearchCity>>
}