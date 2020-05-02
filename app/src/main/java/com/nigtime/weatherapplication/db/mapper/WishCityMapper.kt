/*
 * Сreated by Igor Pokrovsky. 2020/5/2
 */

package com.nigtime.weatherapplication.db.mapper

import android.util.Log
import com.nigtime.weatherapplication.db.table.ReferenceCityTable
import com.nigtime.weatherapplication.db.table.WishCityTable
import com.nigtime.weatherapplication.domain.city.WishCity

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
        Log.d("sas", "in = $listIndex index = $newListIndex")
        return WishCityTable(wishCity.cityId, newListIndex)
    }
}