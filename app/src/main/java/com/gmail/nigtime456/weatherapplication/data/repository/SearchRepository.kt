/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.gmail.nigtime456.weatherapplication.data.repository

import com.gmail.nigtime456.weatherapplication.data.location.SearchCity
import io.reactivex.Single

/**
 * Репозиторий для постраничной загрузки поиска.
 */
interface SearchRepository {

    /**
     * @return int - позиция вставленного города.
     */
    fun insert(searchCity: SearchCity): Single<Int>

    fun searchCities(query: String): Single<List<SearchCity>>
}