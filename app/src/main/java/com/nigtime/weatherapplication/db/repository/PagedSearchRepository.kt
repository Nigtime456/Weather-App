/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.nigtime.weatherapplication.db.repository

import com.nigtime.weatherapplication.db.data.SearchCity
import io.reactivex.Single

interface PagedSearchRepository {

    /**
     * @return int - позиция вставленного города.
     */
    fun insert(searchCity: SearchCity): Single<Int>

    /**
     * Загрузить результатыт [query] с [position] количеством [count]
     */
    fun loadPage(query: String, position: Int, count: Int): Single<List<SearchCity>>
}