/*
 * Сreated by Igor Pokrovsky. 2020/4/28
 */

package com.nigtime.weatherapplication.storage.repository

import com.nigtime.weatherapplication.domain.city.CityForForecast
import com.nigtime.weatherapplication.domain.city.ForecastCitiesRepository
import com.nigtime.weatherapplication.storage.mapper.CityForForecastMapper
import com.nigtime.weatherapplication.storage.service.WishCityDao
import io.reactivex.Flowable

class ForecastCitiesRepositoryImpl constructor(
    private val wishCityDao: WishCityDao,
    private val cityMapper: CityForForecastMapper
) : ForecastCitiesRepository {

    override fun getListCities(): Flowable<List<CityForForecast>> {
        return wishCityDao.getAllIdsAsFlow()
            .map { idList ->
                idList.map(this::mapCity)
            }
    }

    private fun mapCity(cityId: Long): CityForForecast {
        return cityMapper.map(cityId, wishCityDao.getCityName(cityId))
    }
}