/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.nigtime.weatherapplication.db.repository

import com.nigtime.weatherapplication.db.mapper.SearchCityMapper
import com.nigtime.weatherapplication.db.service.ReferenceCityDao
import com.nigtime.weatherapplication.db.service.WishCityDao
import com.nigtime.weatherapplication.db.table.ReferenceCityTable
import com.nigtime.weatherapplication.db.table.WishCityTable
import com.nigtime.weatherapplication.domain.city.PagedSearchRepository
import com.nigtime.weatherapplication.domain.city.SearchCity
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.flatMapIterable

/**
 * Репозиторий хранит состояние в вилде IDs сохраненных городов,
 * поэтому должен содаваться по новому, для каждого поиска
 */
class PagedSearchRepositoryImpl(
    private val referenceCityDao: ReferenceCityDao,
    private val wishCityDao: WishCityDao,
    private val cityMapper: SearchCityMapper
) : PagedSearchRepository {
    private var wishIds: Set<Long>? = null

    override fun insert(searchCity: SearchCity): Single<Int> {
        return Single.fromCallable(this::getMaxListIndex)
            .doOnSuccess { maxIndex ->
                wishCityDao.insert(mapSearchCity(searchCity, maxIndex))
            }
    }

    private fun mapSearchCity(searchCity: SearchCity, maxListIndex: Int): WishCityTable {
        return cityMapper.mapEntity(searchCity, maxListIndex)
    }

    override fun loadPage(query: String, position: Int, count: Int): Single<List<SearchCity>> {
        return Observable.fromCallable {
            referenceCityDao.queryByName(
                getSQLPatternQuery(query),
                position,
                count
            )
        }
            .doOnSubscribe { loadIds() }
            .flatMapIterable()
            .map { item -> mapReferenceCityToData(item, query) }
            .toList()
    }

    private fun loadIds() {
        if (wishIds == null)
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