/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.nigtime.weatherapplication.db.repository

import com.nigtime.weatherapplication.db.mapper.SearchCityMapper
import com.nigtime.weatherapplication.db.service.ReferenceCityDao
import com.nigtime.weatherapplication.db.service.WishCityDao
import com.nigtime.weatherapplication.db.table.ReferenceCityTable
import com.nigtime.weatherapplication.db.table.WishCityTable
import com.nigtime.weatherapplication.domain.city.SearchCity
import com.nigtime.weatherapplication.domain.repository.PagedSearchRepository
import io.reactivex.Single

class PagedSearchRepositoryImpl(
    private val referenceCityDao: ReferenceCityDao,
    private val wishCityDao: WishCityDao,
    private val cityMapper: SearchCityMapper
) : PagedSearchRepository {
    private var wishIds: Set<Long>? = null

    override fun insert(searchCity: SearchCity): Single<Int> {
        return Single.fromCallable { getMaxListIndex() }
            .map { maxListIndex ->
                wishCityDao.insert(mapSearchCity(searchCity, maxListIndex))
                maxListIndex
            }
    }

    private fun mapSearchCity(searchCity: SearchCity, maxListIndex: Int): WishCityTable {
        return cityMapper.mapEntity(searchCity, maxListIndex)
    }

    override fun loadPage(query: String, position: Int, count: Int): Single<List<SearchCity>> {
        return Single.just(wishIds == null)
            .map { noIds -> if (noIds) loadIds() }
            .map { referenceCityDao.queryByName(getSQLPatternQuery(query), position, count) }
            .map { list ->
                list.map { cityTable -> mapReferenceCityToData(cityTable, query) }
            }
    }

    private fun loadIds() {
        wishIds = wishCityDao.getAllIds().toSet()
    }

    private fun getSQLPatternQuery(query: String): String {
        return "%$query%"
    }

    private fun getMaxListIndex(): Int {
        return getNewIndexFromList(wishCityDao.getMaxListIndex())
    }

    private fun getNewIndexFromList(listWithIndex: List<Int>): Int {
        require(listWithIndex.size <= 1) { "list must be contained one or none indexes" }
        return if (listWithIndex.isEmpty()) 0 else listWithIndex.first().inc()
    }

    private fun mapReferenceCityToData(rawRow: ReferenceCityTable, query: String): SearchCity {
        val isWish = wishIds?.contains(rawRow.cityId) ?: false
        return cityMapper.mapDomain(rawRow, isWish, query)
    }

}