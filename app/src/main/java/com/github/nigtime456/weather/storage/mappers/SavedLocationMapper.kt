/*
 * Сreated by Igor Pokrovsky. 2020/5/2
 */

package com.github.nigtime456.weather.storage.mappers

import com.github.nigtime456.weather.data.location.SavedLocation
import com.github.nigtime456.weather.storage.table.SavedLocationTable
import javax.inject.Inject

class SavedLocationMapper @Inject constructor() {

    private fun mapDomain(entity: SavedLocationTable): SavedLocation {
        return SavedLocation(
            entity.id,
            entity.lat,
            entity.lon,
            entity.listIndex,
            entity.name,
            entity.state,
            entity.country
        )
    }

    fun mapDomainList(list: List<SavedLocationTable>): List<SavedLocation> {
        return list.map(this::mapDomain)
    }

    fun mapEntity(location: SavedLocation): SavedLocationTable {
        return SavedLocationTable(
            location.id,
            location.lat,
            location.lon,
            location.listIndex,
            location.name,
            location.state,
            location.country
        )
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
        return SavedLocationTable(
            location.id,
            location.lat,
            location.lon,
            listIndex,
            location.name,
            location.state,
            location.country
        )
    }

}