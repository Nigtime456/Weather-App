/*
 * Сreated by Igor Pokrovsky. 2020/5/2
 */

package com.nigtime.weatherapplication.storage.mappers

import com.nigtime.weatherapplication.domain.location.SearchCity
import com.nigtime.weatherapplication.storage.tables.ReferenceCityTable
import com.nigtime.weatherapplication.storage.tables.SavedLocationTable

class SearchCityMapper {

    private fun mapDomain(
        entity: ReferenceCityTable,
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
        list: List<ReferenceCityTable>,
        savedIds: Set<Long>,
        query: String
    ): List<SearchCity> {
        return list.map { item -> mapDomain(item, savedIds.contains(item.cityId), query) }
    }

    fun mapLocationsTable(searchCity: SearchCity, listIndex: Int): SavedLocationTable {
        return SavedLocationTable(
            listIndex,
            searchCity.cityId,
            searchCity.name,
            searchCity.stateName,
            searchCity.countryName
        )
    }
}