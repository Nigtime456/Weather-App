/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.nigtime.weatherapplication.db.repository

import com.nigtime.weatherapplication.domain.database.SearchCity
import com.nigtime.weatherapplication.db.tables.ReferenceCityTable
import com.nigtime.weatherapplication.db.tables.WishCityTable
import com.nigtime.weatherapplication.db.service.ReferenceCityDao
import com.nigtime.weatherapplication.db.service.WishCityDao
import com.nigtime.weatherapplication.domain.repository.database.PagedSearchRepository
import io.reactivex.Single

class PagedSearchRepositoryImpl(
    private val referenceCityDao: ReferenceCityDao,
    private val wishCityDao: WishCityDao
) : PagedSearchRepository {
    private var wishIds: Set<Long>? = null

    override fun insert(searchCity: SearchCity): Single<Int> {
        return Single.fromCallable { getMaxListIndex() }
            .map { maxListIndex ->
                wishCityDao.insert(searchCity.toWishCityEntity(maxListIndex))
                maxListIndex
            }
    }

    override fun loadPage(query: String, position: Int, count: Int): Single<List<SearchCity>> {
        return Single.just(wishIds == null)
            .map { noIds -> if (noIds) loadIds() }
            .map { referenceCityDao.queryByName(getSQLPatternQuery(query), position, count) }
            .map { list ->
                mapEntityListToData(list, query)
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

    //TODO mapper
    private fun mapEntityListToData(
        list: List<ReferenceCityTable>,
        query: String
    ): List<SearchCity> {
        return list.map { cityTable ->
            val isWish = wishIds!!.contains(cityTable.cityId)
            cityTable.toSearchCityData(isWish, query)
        }
    }

    private fun ReferenceCityTable.toSearchCityData(isWish: Boolean, query: String): SearchCity {
        return SearchCity(cityId, name, stateName, countryName, isWish, query)
    }


    private fun SearchCity.toWishCityEntity(listIndex: Int): WishCityTable {
        return WishCityTable(cityId, listIndex)
    }
}