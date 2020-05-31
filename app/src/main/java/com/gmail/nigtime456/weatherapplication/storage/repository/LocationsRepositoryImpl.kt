/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.gmail.nigtime456.weatherapplication.storage.repository

import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.domain.repository.LocationsRepository
import com.gmail.nigtime456.weatherapplication.storage.mappers.SavedLocationMapper
import com.gmail.nigtime456.weatherapplication.storage.service.SavedLocationsDao
import com.gmail.nigtime456.weatherapplication.tools.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class LocationsRepositoryImpl @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val savedLocationsDao: SavedLocationsDao,
    private val mapper: SavedLocationMapper
) : LocationsRepository {

    private lateinit var cachedLocations: Observable<List<SavedLocation>>

    override fun getLocations(): Observable<List<SavedLocation>> {
        return if (::cachedLocations.isInitialized) {
            Timber.tag("LocationRepository").d("cached")
            cachedLocations
        } else {
            createCachedLocations()
            cachedLocations
        }
    }

    private fun createCachedLocations() {
        cachedLocations = savedLocationsDao.getAll()
            .map(mapper::mapDomainList)
            .subscribeOn(schedulerProvider.database())
            .observeOn(schedulerProvider.ui())
            .doOnDispose {
                Timber.tag("LocationRepository").d("dispose")
            }
            .replay(1)
            .autoConnect()
    }

    override fun hasLocations(): Single<Boolean> {
        return Single.fromCallable(savedLocationsDao::getOneRow)
            .map(List<*>::isNotEmpty)
            .subscribeOn(schedulerProvider.database())
            .observeOn(schedulerProvider.ui())
    }

    override fun delete(item: SavedLocation): Completable {
        return Single.just(item)
            .map(mapper::mapEntity)
            .doOnSuccess { entity -> savedLocationsDao.delete(entity) }
            .ignoreElement()
            .subscribeOn(schedulerProvider.database())
            .observeOn(schedulerProvider.ui())
    }

    override fun replaceAll(items: List<SavedLocation>): Completable {
        return Single.just(items)
            .map(mapper::mapListEntity)
            .doOnSuccess { list -> savedLocationsDao.updateAll(list) }
            .ignoreElement()
            .subscribeOn(schedulerProvider.database())
            .observeOn(schedulerProvider.ui())
    }
}