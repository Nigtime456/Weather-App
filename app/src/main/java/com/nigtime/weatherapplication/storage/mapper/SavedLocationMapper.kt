/*
 * Сreated by Igor Pokrovsky. 2020/5/2
 */

package com.nigtime.weatherapplication.storage.mapper

import com.nigtime.weatherapplication.domain.location.SavedLocation
import com.nigtime.weatherapplication.storage.table.LocationsTable

class SavedLocationMapper {

    private fun mapDomain(entity: LocationsTable): SavedLocation {
        return SavedLocation.City(
            entity.listIndex,
            entity.id,
            entity.name,
            entity.stateName,
            entity.countryName
        )
    }

    fun mapDomainList(list: List<LocationsTable>): List<SavedLocation> {
        return list.map { item -> mapDomain(item) }
    }

    fun mapEntity(location: SavedLocation): LocationsTable {
        return when (location) {
            is SavedLocation.City -> {
                LocationsTable(
                    location.listIndex,
                    location.cityId,
                    location.cityName,
                    location.stateName,
                    location.countryName
                )
            }
        }
    }

    fun mapListEntity(list: List<SavedLocation>): List<LocationsTable> {
        return list.map { item -> mapEntity(item) }
    }
}