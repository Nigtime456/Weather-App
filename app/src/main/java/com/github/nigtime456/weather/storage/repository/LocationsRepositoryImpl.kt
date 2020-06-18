/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.github.nigtime456.weather.storage.repository

import com.github.nigtime456.weather.data.location.SavedLocation
import com.github.nigtime456.weather.data.repository.LocationsRepository
import com.github.nigtime456.weather.storage.mappers.SavedLocationMapper
import com.github.nigtime456.weather.storage.service.SavedLocationsDao
import com.github.nigtime456.weather.utils.rx.SchedulerProvider
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

    private companion object {
        const val TAG = "location_repository"
    }

    private lateinit var cachedLocations: Observable<List<SavedLocation>>

    override fun getLocations(): Observable<List<SavedLocation>> {
        return if (::cachedLocations.isInitialized) {
            Timber.tag(TAG).d("from cache")
            cachedLocations
        } else {
            createCachedLocations()
            cachedLocations
        }
    }

    private fun createCachedLocations() {
        cachedLocations = savedLocationsDao.getAll()
            .map(mapper::mapDomainList)
            .doOnEach { Timber.tag(TAG).d("each = $it") }
            .subscribeOn(schedulerProvider.database())
            .observeOn(schedulerProvider.ui())
            .doOnDispose { Timber.tag(TAG).d("dispose") }
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

    override fun update(items: List<SavedLocation>): Completable {
        return Single.just(items)
            .map(mapper::mapListEntity)
            .doOnSuccess { list -> savedLocationsDao.updateAll(list) }
            .ignoreElement()
            .subscribeOn(schedulerProvider.database())
            .observeOn(schedulerProvider.ui())
    }
}