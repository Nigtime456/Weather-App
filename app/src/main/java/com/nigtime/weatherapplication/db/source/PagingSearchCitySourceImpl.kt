/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.nigtime.weatherapplication.db.source

import com.nigtime.weatherapplication.db.data.SearchCityData
import com.nigtime.weatherapplication.db.service.GeoCityDao
import com.nigtime.weatherapplication.db.tables.GeoCityTable
import io.reactivex.Single

/**
 * Подгружает данные по одному запросу
 *
 * @param query - текст выборки
 * @param selectedCitiesIds - множество идентификаторов уже сохраненных городов.
 */

class PagingSearchCitySourceImpl(
    private val geoCityDao: GeoCityDao,
    private val selectedCitiesIds: Set<Long>,
    private val query: String
) : PagingSearchCitySource {
    private val queryPattern: String = "%$query%"

    override fun loadNextPage(startPosition: Int, loadSize: Int): Single<List<SearchCityData>> {
        return Single.fromCallable { geoCityDao.queryByName(queryPattern, startPosition, loadSize) }
            .map { list ->
                list.map { geoCityTable ->
                    val isSelect = selectedCitiesIds.contains(geoCityTable.cityId)
                    geoCityTable.toSearchData(isSelect, query)
                }
            }
    }

    private fun GeoCityTable.toSearchData(isSelect: Boolean, query: String): SearchCityData {
        return SearchCityData(cityId, name, stateName, countryName, isSelect, query)
    }
}