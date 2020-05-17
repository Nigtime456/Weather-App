/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.nigtime.weatherapplication.storage.repository

import com.nigtime.weatherapplication.domain.location.PagedSearchRepository
import com.nigtime.weatherapplication.domain.location.SearchCity
import com.nigtime.weatherapplication.storage.mapper.SearchCityMapper
import com.nigtime.weatherapplication.storage.service.ReferenceCitiesDao
import com.nigtime.weatherapplication.storage.service.SavedLocationsDao
import com.nigtime.weatherapplication.storage.table.ReferenceCitiesTable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.flatMapIterable

/**
 * Репозиторий хранит состояние в вилде IDs сохраненных городов,
 * поэтому должен содаваться по новому, для каждого поиска
 */
class PagedSearchRepositoryImpl(
    private val referenceCitiesDao: ReferenceCitiesDao,
    private val savedLocationsDao: SavedLocationsDao,
    private val mapper: SearchCityMapper
) : PagedSearchRepository {
    private var savedCitiesIds: Set<Long>? = null

    override fun insert(searchCity: SearchCity): Single<Int> {
        return Single.fromCallable(this::getMaxListIndex)
            .doOnSuccess { maxIndex ->
                savedLocationsDao.insert(mapper.mapEntity(searchCity, maxIndex))
            }
    }

    override fun loadPage(query: String, position: Int, count: Int): Single<List<SearchCity>> {
        return Observable.fromCallable {
            referenceCitiesDao.queryByName(
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
        if (savedCitiesIds == null)
            savedCitiesIds = savedLocationsDao.getAllIds().toSet()
    }

    private fun getSQLPatternQuery(query: String): String {
        return "%$query%"
    }

    private fun getMaxListIndex(): Int {
        return getNewIndexFromList(savedLocationsDao.getMaxListIndex())
    }

    private fun getNewIndexFromList(listWithIndex: List<Int>): Int {
        require(listWithIndex.size <= 1) { "list must be contained one or none indexes" }
        return if (listWithIndex.isEmpty()) 0 else listWithIndex.first().inc()
    }

    private fun mapReferenceCityToData(rawRow: ReferenceCitiesTable, query: String): SearchCity {
        val isSaved = savedCitiesIds?.contains(rawRow.cityId) ?: false
        return mapper.mapDomain(rawRow, isSaved, query)
    }

}