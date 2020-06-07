/*
 * Сreated by Igor Pokrovsky. 2020/5/2
 */

package com.gmail.nigtime456.weatherapplication.storage.mappers

import com.gmail.nigtime456.weatherapplication.data.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.storage.table.SavedLocationTable
import javax.inject.Inject

class SavedLocationMapper @Inject constructor() {

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
        return list.map(this::mapDomain)
    }

    fun mapEntity(location: SavedLocation): SavedLocationTable {
        return when (location) {
            is SavedLocation.City -> {
                SavedLocationTable(
                    location.cityId,
                    location.listIndex,
                    location.cityName,
                    location.stateName,
                    location.countryName
                )
            }
        }
    }

    /**
     * Что бы учесть изменений позиций - нужно перезаписать индексы.
     */
    fun mapListEntity(list: List<SavedLocation>): List<SavedLocationTable> {
        return list.mapIndexed(this::mapEntityWithListIndex)
    }

    private fun mapEntityWithListIndex(
        listIndex: Int,
        location: SavedLocation
    ): SavedLocationTable {
        return when (location) {
            is SavedLocation.City -> {
                SavedLocationTable(
                    location.cityId,
                    listIndex,
                    location.cityName,
                    location.stateName,
                    location.countryName
                )
            }
        }
    }


}