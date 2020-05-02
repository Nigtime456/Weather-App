/*
 * Сreated by Igor Pokrovsky. 2020/5/2
 */

package com.nigtime.weatherapplication.db.mapper

import com.nigtime.weatherapplication.db.table.ReferenceCityTable
import com.nigtime.weatherapplication.db.table.WishCityTable
import com.nigtime.weatherapplication.domain.city.SearchCity

class SearchCityMapper {

    fun mapDomain(raw: ReferenceCityTable, isWish: Boolean, query: String): SearchCity {
        return SearchCity(raw.cityId, raw.name, raw.stateName, raw.countryName, isWish, query)
    }

    fun mapEntity(searchCity: SearchCity, listIndex: Int): WishCityTable {
        return WishCityTable(searchCity.cityId, listIndex)
    }

}