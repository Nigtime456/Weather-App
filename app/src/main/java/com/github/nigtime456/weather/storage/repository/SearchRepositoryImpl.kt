/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.github.nigtime456.weather.storage.repository

import com.github.nigtime456.weather.data.location.SearchCity
import com.github.nigtime456.weather.data.repository.SearchRepository
import com.github.nigtime456.weather.storage.mappers.SearchCityMapper
import com.github.nigtime456.weather.storage.service.ReferenceCitiesDao
import com.github.nigtime456.weather.storage.service.SavedLocationsDao
import com.github.nigtime456.weather.utils.rx.SchedulerProvider
import io.reactivex.Single
import javax.inject.Inject

/**
 * Репозиторий хранит состояние в вилде IDs сохраненных городов,
 * поэтому должен содаваться по новому, для каждого поиска
 */
class SearchRepositoryImpl @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val referenceCitiesDao: ReferenceCitiesDao,
    private val savedLocationsDao: SavedLocationsDao,
    private val mapper: SearchCityMapper
) : SearchRepository {
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

    override fun searchCities(query: String): Single<List<SearchCity>> {
        return Single.just(getSQLPatternQuery(query))
            .map(referenceCitiesDao::searchByName)
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