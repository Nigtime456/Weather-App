/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.nigtime.weatherapplication.storage.repository

import com.nigtime.weatherapplication.domain.location.SavedLocation
import com.nigtime.weatherapplication.domain.location.SavedLocationsRepository
import com.nigtime.weatherapplication.storage.mapper.SavedLocationMapper
import com.nigtime.weatherapplication.storage.service.ReferenceCitiesDao
import com.nigtime.weatherapplication.storage.service.SavedLocationsDao
import com.nigtime.weatherapplication.storage.table.LocationTable
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class SavedLocationRepositoryImpl constructor(
    private val referenceCitiesDao: ReferenceCitiesDao,
    private val savedLocationsDao: SavedLocationsDao,
    private val mapper: SavedLocationMapper
) : SavedLocationsRepository {

    override fun getLocations(): Single<List<SavedLocation>> {
        return Single.fromCallable(savedLocationsDao::getAll)
            .map(this::getListCityByIds)
    }

    override fun getLocationsAsFlowable(): Flowable<List<SavedLocation>> {
        return savedLocationsDao.getAllAsFlowable()
            .map(this::getListCityByIds)
    }

    private fun getListCityByIds(sourceList: List<LocationTable>): List<SavedLocation> {
        return sourceList.map { savedLocation ->
            val referenceCity = referenceCitiesDao.getById(savedLocation.id)
            mapper.mapDomain(referenceCity, savedLocation)
        }
    }

    override fun hasLocations(): Single<Boolean> {
        return Single.fromCallable(savedLocationsDao::getOneRow)
            .map { list -> list.isNotEmpty() }
    }

    override fun delete(item: SavedLocation): Completable {
        return Completable.fromAction { savedLocationsDao.delete(mapper.mapEntity(item)) }
    }

    override fun replaceAll(items: List<SavedLocation>): Completable {
        return Single.just(items)
            .map(mapListToEntity())
            .doOnSuccess(savedLocationsDao::insertAndReplaceAll)
            .ignoreElement()
    }

    private fun mapListToEntity(): (List<SavedLocation>) -> List<LocationTable> {
        return { list ->
            list.mapIndexed { index, savedLocation ->
                mapper.mapEntity(savedLocation, index)
            }
        }
    }


}