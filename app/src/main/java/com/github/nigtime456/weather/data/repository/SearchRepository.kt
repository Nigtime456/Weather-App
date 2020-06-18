/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.github.nigtime456.weather.data.repository

import com.github.nigtime456.weather.data.location.SearchCity
import io.reactivex.Single

/**
 * Репозиторий для постраничной загрузки поиска.
 */
interface SearchRepository {

    /**
     * @return позиция вставленного города.
     */
    fun insert(searchCity: SearchCity): Single<Int>

    fun searchCities(query: String): Single<List<SearchCity>>
}