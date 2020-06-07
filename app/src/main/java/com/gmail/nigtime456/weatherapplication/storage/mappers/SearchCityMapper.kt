/*
 * Сreated by Igor Pokrovsky. 2020/5/2
 */

package com.gmail.nigtime456.weatherapplication.storage.mappers

import com.gmail.nigtime456.weatherapplication.data.location.SearchCity
import com.gmail.nigtime456.weatherapplication.storage.table.ReferenceCityTable
import com.gmail.nigtime456.weatherapplication.storage.table.SavedLocationTable
import javax.inject.Inject

class SearchCityMapper @Inject constructor() {

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
            searchCity.cityId,
            listIndex,
            searchCity.name,
            searchCity.stateName,
            searchCity.countryName
        )
    }
}