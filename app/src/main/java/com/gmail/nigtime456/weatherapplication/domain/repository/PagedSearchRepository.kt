/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.gmail.nigtime456.weatherapplication.domain.repository

import com.gmail.nigtime456.weatherapplication.domain.location.SearchCity
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