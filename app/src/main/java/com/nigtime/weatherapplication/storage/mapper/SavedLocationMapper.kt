/*
 * Сreated by Igor Pokrovsky. 2020/5/2
 */

package com.nigtime.weatherapplication.storage.mapper

import com.nigtime.weatherapplication.domain.location.SavedLocation
import com.nigtime.weatherapplication.storage.table.LocationTable
import com.nigtime.weatherapplication.storage.table.ReferenceCitiesTable

class SavedLocationMapper {
    fun mapDomain(rawReference: ReferenceCitiesTable, rawLocation: LocationTable): SavedLocation {
        return SavedLocation.City(
            rawLocation.listIndex,
            rawLocation.id,
            rawReference.name,
            rawReference.stateName,
            rawReference.countryName
        )
    }

    fun mapEntity(rawLocation: SavedLocation, listIndex: Int = -1): LocationTable {
        val newListIndex = if (listIndex != -1) {
            listIndex
        } else {
            rawLocation.listIndex
        }
        return LocationTable(rawLocation.getKey(), newListIndex)
    }
}