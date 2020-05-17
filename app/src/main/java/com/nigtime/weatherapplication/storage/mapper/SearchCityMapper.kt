/*
 * Сreated by Igor Pokrovsky. 2020/5/2
 */

package com.nigtime.weatherapplication.storage.mapper

import com.nigtime.weatherapplication.domain.city.SearchCity
import com.nigtime.weatherapplication.storage.table.ReferenceCityTable
import com.nigtime.weatherapplication.storage.table.WishCityTable

class SearchCityMapper {

    fun mapDomain(raw: ReferenceCityTable, isWish: Boolean, query: String): SearchCity {
        return SearchCity(raw.cityId, raw.name, raw.stateName, raw.countryName, isWish, query)
    }

    fun mapEntity(searchCity: SearchCity, listIndex: Int): WishCityTable {
        return WishCityTable(searchCity.cityId, listIndex)
    }

}