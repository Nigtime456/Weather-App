/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.nigtime.weatherapplication.db.repository

import com.nigtime.weatherapplication.db.mapper.CityForForecastMapper
import com.nigtime.weatherapplication.db.service.WishCityDao
import com.nigtime.weatherapplication.domain.city.CityForForecast
import com.nigtime.weatherapplication.domain.repository.ForecastCitiesRepository
import io.reactivex.Single

class ForecastCitiesRepositoryImpl constructor(
    private val wishCityDao: WishCityDao,
    private val cityMapper: CityForForecastMapper
) : ForecastCitiesRepository {

    override fun getListCities(): Single<List<CityForForecast>> {
        return Single.fromCallable { wishCityDao.getAllIds() }
            .map { ids -> ids.map(this::mapCity) }
    }

    private fun mapCity(cityId: Long): CityForForecast {
        return cityMapper.map(cityId, wishCityDao.getCityName(cityId))
    }
}