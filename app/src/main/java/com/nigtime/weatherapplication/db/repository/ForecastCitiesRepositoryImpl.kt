/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.nigtime.weatherapplication.db.repository

import com.nigtime.weatherapplication.db.data.CityForForecast
import com.nigtime.weatherapplication.db.service.ReferenceCityDao
import com.nigtime.weatherapplication.db.service.WishCityDao
import io.reactivex.Single

class ForecastCitiesRepositoryImpl constructor(
    private val referenceCityDao: ReferenceCityDao,
    private val wishCityDao: WishCityDao
) : ForecastCitiesRepository {

    override fun getListCityForForecast(): Single<List<CityForForecast>> {
        return Single.fromCallable { wishCityDao.getAllIds() }
            .map { ids -> ids.map(this::mapCity) }
    }

    private fun mapCity(cityId: Long): CityForForecast {
        return CityForForecast(cityId, referenceCityDao.getNameById(cityId))
    }
}