/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.gmail.nigtime456.weatherapplication.storage.repository

import com.gmail.nigtime456.weatherapplication.domain.location.SearchCity
import com.gmail.nigtime456.weatherapplication.domain.repository.PagedSearchRepository
import com.gmail.nigtime456.weatherapplication.storage.mappers.SearchCityMapper
import com.gmail.nigtime456.weatherapplication.storage.service.ReferenceCitiesDao
import com.gmail.nigtime456.weatherapplication.storage.service.SavedLocationsDao
import com.gmail.nigtime456.weatherapplication.tools.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Репозиторий хранит состояние в вилде IDs сохраненных городов,
 * поэтому должен содаваться по новому, для каждого поиска
 */
class PagedSearchRepositoryImpl @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val referenceCitiesDao: ReferenceCitiesDao,
    private val savedLocationsDao: SavedLocationsDao,
    private val mapper: SearchCityMapper
) : PagedSearchRepository {
    private lateinit var savedCitiesIds: Set<Long>

    override fun insert(searchCity: SearchCity): Single<Int> {
        return Single.fromCallable(this::getMaxListIndex)
            .doOnSuccess { maxIndex ->
                savedLocationsDao.insert(mapper.mapLocationsTable(searchCity, maxIndex))
            }
            .subscribeOn(schedulerProvider.database())
            .observeOn(schedulerProvider.ui())
    }


    private fun getMaxListIndex(): Int {
        return getNewIndexFromList(savedLocationsDao.getMaxListIndex())
    }

    private fun getNewIndexFromList(listWithIndex: List<Int>): Int {
        return if (listWithIndex.isEmpty()) 0 else listWithIndex.first().inc()
    }

    override fun loadPage(query: String): Observable<List<SearchCity>> {
        return Observable.just(getSQLPatternQuery(query))
            .map(referenceCitiesDao::queryByName)
            .map { list -> mapper.mapDomainList(list, getSavedLocationsIds(), query) }
            .subscribeOn(schedulerProvider.database())
            .observeOn(schedulerProvider.ui())
    }

    private fun getSQLPatternQuery(query: String): String {
        return "%$query%"
    }

    private fun getSavedLocationsIds(): Set<Long> {
        if (!::savedCitiesIds.isInitialized)
            savedCitiesIds = savedLocationsDao.getAllIds().toSet()
        return savedCitiesIds
    }
}