/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.nigtime.weatherapplication.db.repository

import com.nigtime.weatherapplication.db.data.WishCity
import com.nigtime.weatherapplication.db.entity.ReferenceCityTable
import com.nigtime.weatherapplication.db.entity.WishCityTable
import com.nigtime.weatherapplication.db.service.ReferenceCityDao
import com.nigtime.weatherapplication.db.service.WishCityDao
import io.reactivex.Completable
import io.reactivex.Single

class WishCitiesRepositoryImpl constructor(
    private val referenceCityDao: ReferenceCityDao,
    private val wishCityDao: WishCityDao
) : WishCitiesRepository {

    override fun getWishCitiesList(): Single<List<WishCity>> {
        return Single.fromCallable { wishCityDao.getAll() }
            .map(this::getWishListByIds)
    }

    private fun getWishListByIds(sourceList: List<WishCityTable>): List<WishCity> {
        return sourceList.map { wishCityEntity ->
            val referenceCityTable = referenceCityDao.getById(wishCityEntity.cityId)
            referenceCityTable.toWishCityData(wishCityEntity.listIndex)
        }
    }

    override fun hasWishCities(): Single<Boolean> {
        return Single.fromCallable { wishCityDao.getOneRow() }
            .map { list -> list.isNotEmpty() }
    }

    override fun delete(item: WishCity): Completable {
        return Completable.fromAction { wishCityDao.delete(item.toEntity()) }
    }

    override fun replaceAll(items: List<WishCity>): Completable {
        return Single.just(items)
            .map { list -> list.mapIndexed { index, wishCity -> wishCity.toEntity(index) } }
            .doOnSuccess(wishCityDao::insertAndReplaceAll)
            .ignoreElement()
    }

    private fun ReferenceCityTable.toWishCityData(index: Int): WishCity {
        return WishCity(cityId, index, name, stateName, countryName)
    }

    private fun WishCity.toEntity(): WishCityTable {
        return WishCityTable(cityId, listIndex)
    }

    private fun WishCity.toEntity(newIndex: Int): WishCityTable {
        return WishCityTable(cityId, newIndex)
    }
}