/*
 * Сreated by Igor Pokrovsky. 2020/5/2
 */

package com.nigtime.weatherapplication.storage.mapper

import com.nigtime.weatherapplication.domain.city.WishCity
import com.nigtime.weatherapplication.storage.table.ReferenceCityTable
import com.nigtime.weatherapplication.storage.table.WishCityTable

class WishCityMapper {
    fun mapDomain(rawReference: ReferenceCityTable, rawWish: WishCityTable): WishCity {
        return WishCity(
            rawWish.cityId,
            rawWish.listIndex,
            rawReference.name,
            rawReference.stateName,
            rawReference.countryName
        )
    }

    fun mapEntity(wishCity: WishCity, listIndex: Int = -1): WishCityTable {
        val newListIndex = if (listIndex != -1) {
            listIndex
        } else {
            wishCity.listIndex
        }
        return WishCityTable(wishCity.cityId, newListIndex)
    }
}