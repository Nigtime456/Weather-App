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
    fun loadPage(startPosition: Int, loadSize: Int): Single<List<SearchCityData>>
}