/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.nigtime.weatherapplication.storage.repository

import com.nigtime.weatherapplication.domain.location.ForecastLocation
import com.nigtime.weatherapplication.domain.location.ForecastLocationsRepository
import com.nigtime.weatherapplication.storage.mapper.ForecastLocationMapper
import com.nigtime.weatherapplication.storage.service.SavedLocationsDao
import io.reactivex.Flowable

class ForecastLocationsRepositoryImpl constructor(
    private val savedLocationsDao: SavedLocationsDao,
    private val mapper: ForecastLocationMapper
) : ForecastLocationsRepository {

    override fun getLocations(): Flowable<List<ForecastLocation>> {
        return savedLocationsDao.getAllIdsAsFlow()
            .map { listIds ->
                listIds.map(this::mapLocation)
            }
    }

    private fun mapLocation(id: Long): ForecastLocation {
        return mapper.map(id, savedLocationsDao.getCityName(id))
    }
}