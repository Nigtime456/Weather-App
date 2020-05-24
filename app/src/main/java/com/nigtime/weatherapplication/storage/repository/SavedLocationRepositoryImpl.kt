/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.nigtime.weatherapplication.storage.repository

import android.util.Log
import com.nigtime.weatherapplication.common.rx.SchedulerProvider
import com.nigtime.weatherapplication.domain.location.SavedLocation
import com.nigtime.weatherapplication.domain.location.SavedLocationsRepository
import com.nigtime.weatherapplication.storage.mappers.SavedLocationMapper
import com.nigtime.weatherapplication.storage.service.ReferenceCitiesDao
import com.nigtime.weatherapplication.storage.service.SavedLocationsDao
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class SavedLocationRepositoryImpl constructor(
    private val schedulerProvider: SchedulerProvider,
    private val referenceCitiesDao: ReferenceCitiesDao,
    private val savedLocationsDao: SavedLocationsDao,
    private val mapper: SavedLocationMapper
) : SavedLocationsRepository {

    private lateinit var cachedLocations: Observable<List<SavedLocation>>

    override fun getLocations(): Observable<List<SavedLocation>> {
        return if (::cachedLocations.isInitialized) {
            Log.d("sas", "cache locations")
            cachedLocations
        } else {
            createCachedLocations()
            cachedLocations
        }
    }

    private fun createCachedLocations() {
        cachedLocations = savedLocationsDao.getAll()
            .doOnEach {
                Log.d("sas", "LOAD = $it")
            }
            .map(mapper::mapDomainList)
            .subscribeOn(schedulerProvider.database())
            .observeOn(schedulerProvider.ui())
            .replay(1)
            .refCount()
            .doOnEach {
                Log.d("sas", "REF = $it")
            }
    }

    override fun getLocationsOnce(): Single<List<SavedLocation>> {
        return savedLocationsDao.getAllOnce()
            .map(mapper::mapDomainList)
            .subscribeOn(schedulerProvider.database())
            .observeOn(schedulerProvider.ui())
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