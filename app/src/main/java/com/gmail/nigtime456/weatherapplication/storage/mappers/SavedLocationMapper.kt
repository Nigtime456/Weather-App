/*
 * Сreated by Igor Pokrovsky. 2020/5/2
 */

package com.gmail.nigtime456.weatherapplication.storage.mappers

import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.storage.tables.SavedLocationTable

class SavedLocationMapper {

    private fun mapDomain(entity: SavedLocationTable): SavedLocation {
        return SavedLocation.City(
            entity.listIndex,
            entity.id,
            entity.name,
            entity.stateName,
            entity.countryName
        )
    }

    fun mapDomainList(list: List<SavedLocationTable>): List<SavedLocation> {
        return list.map { item -> mapDomain(item) }
    }

    fun mapEntity(location: SavedLocation): SavedLocationTable {
        return when (location) {
            is SavedLocation.City -> {
                SavedLocationTable(
                    location.listIndex,
                    location.cityId,
                    location.cityName,
                    location.stateName,
                    location.countryName
                )
            }
        }
    }

    fun mapListEntity(list: List<SavedLocation>): List<SavedLocationTable> {
        return list.map { item -> mapEntity(item) }
    }
}