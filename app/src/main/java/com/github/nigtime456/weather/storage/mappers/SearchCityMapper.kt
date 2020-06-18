/*
 * Сreated by Igor Pokrovsky. 2020/5/2
 */

package com.github.nigtime456.weather.storage.mappers

import com.github.nigtime456.weather.data.location.SearchCity
import com.github.nigtime456.weather.storage.table.ReferenceCityTable
import com.github.nigtime456.weather.storage.table.SavedLocationTable
import javax.inject.Inject

class SearchCityMapper @Inject constructor() {

    private fun mapDomain(
        entity: ReferenceCityTable,
        isSaved: Boolean,
        query: String
    ): SearchCity {
        return SearchCity(
            entity.cityId,
            entity.lat,
            entity.lon,
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
            searchCity.id,
            searchCity.lat,
            searchCity.lon,
            listIndex,
            searchCity.name,
            searchCity.state,
            searchCity.country
        )
    }
}