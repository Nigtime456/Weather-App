/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.nigtime.weatherapplication.storage.repository

import com.nigtime.weatherapplication.domain.city.WishCitiesRepository
import com.nigtime.weatherapplication.domain.city.WishCity
import com.nigtime.weatherapplication.storage.mapper.WishCityMapper
import com.nigtime.weatherapplication.storage.service.ReferenceCityDao
import com.nigtime.weatherapplication.storage.service.WishCityDao
import com.nigtime.weatherapplication.storage.table.WishCityTable
import io.reactivex.Completable
import io.reactivex.Single

class WishCitiesRepositoryImpl constructor(
    private val referenceCityDao: ReferenceCityDao,
    private val wishCityDao: WishCityDao,
    private val cityMapper: WishCityMapper
) : WishCitiesRepository {

    override fun getListCities(): Single<List<WishCity>> {
        return Single.fromCallable(wishCityDao::getAll)
            .map(this::getWishListByIds)
    }

    private fun getWishListByIds(sourceList: List<WishCityTable>): List<WishCity> {
        return sourceList.map { wishCityEntity ->
            val referenceCityTable = referenceCityDao.getById(wishCityEntity.cityId)
            cityMapper.mapDomain(referenceCityTable, wishCityEntity)
        }
    }

    override fun hasCities(): Single<Boolean> {
        return Single.fromCallable(wishCityDao::getOneRow)
            .map { list -> list.isNotEmpty() }
    }

    override fun delete(item: WishCity): Completable {
        return Completable.fromAction { wishCityDao.delete(cityMapper.mapEntity(item)) }
    }

    override fun replaceAll(items: List<WishCity>): Completable {
        return Single.just(items)
            .map(mapListToEntity())
            .doOnSuccess(wishCityDao::insertAndReplaceAll)
            .ignoreElement()
    }

    private fun mapListToEntity(): (List<WishCity>) -> List<WishCityTable> {
        return { list ->
            list.mapIndexed { index, wishCity ->
                cityMapper.mapEntity(wishCity, index)
            }
        }
    }


}