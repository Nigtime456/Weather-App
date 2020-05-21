/*
 * Сreated by Igor Pokrovsky. 2020/5/2
 */

package com.nigtime.weatherapplication.storage.mapper

import com.nigtime.weatherapplication.domain.location.SearchCity
import com.nigtime.weatherapplication.storage.table.LocationsTable
import com.nigtime.weatherapplication.storage.table.ReferenceCitiesTable

class SearchCityMapper {

    private fun mapDomain(
        entity: ReferenceCitiesTable,
        isSaved: Boolean,
        query: String
    ): SearchCity {
        return SearchCity(
            entity.cityId,
            entity.name,
            entity.stateName,
            entity.countryName,
            isSaved,
            query
        )
    }

    fun mapDomainList(
        list: List<ReferenceCitiesTable>,
        savedIds: Set<Long>,
        query: String
    ): List<SearchCity> {
        return list.map { item -> mapDomain(item, savedIds.contains(item.cityId), query) }
    }

    fun mapLocationsTable(searchCity: SearchCity, listIndex: Int): LocationsTable {
        return LocationsTable(
            listIndex,
            searchCity.cityId,
            searchCity.name,
            searchCity.stateName,
            searchCity.countryName
        )
    }
}