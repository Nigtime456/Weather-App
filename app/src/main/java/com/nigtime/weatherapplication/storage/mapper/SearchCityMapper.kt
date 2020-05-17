/*
 * Сreated by Igor Pokrovsky. 2020/5/2
 */

package com.nigtime.weatherapplication.storage.mapper

import com.nigtime.weatherapplication.domain.location.SearchCity
import com.nigtime.weatherapplication.storage.table.LocationTable
import com.nigtime.weatherapplication.storage.table.ReferenceCitiesTable

class SearchCityMapper {

    fun mapDomain(raw: ReferenceCitiesTable, isSaved: Boolean, query: String): SearchCity {
        return SearchCity(raw.cityId, raw.name, raw.stateName, raw.countryName, isSaved, query)
    }

    fun mapEntity(searchCity: SearchCity, listIndex: Int): LocationTable {
        return LocationTable(searchCity.cityId, listIndex)
    }

}