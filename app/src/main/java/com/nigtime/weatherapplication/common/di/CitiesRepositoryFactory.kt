/*
 * Сreated by Igor Pokrovsky. 2020/4/28 
 */

package com.nigtime.weatherapplication.common.di

import com.nigtime.weatherapplication.common.App
import com.nigtime.weatherapplication.db.mapper.CityForForecastMapper
import com.nigtime.weatherapplication.db.mapper.SearchCityMapper
import com.nigtime.weatherapplication.db.mapper.WishCityMapper
import com.nigtime.weatherapplication.db.repository.ForecastCitiesRepositoryImpl
import com.nigtime.weatherapplication.db.repository.PagedSearchRepositoryImpl
import com.nigtime.weatherapplication.db.repository.WishCitiesRepositoryImpl
import com.nigtime.weatherapplication.db.service.ReferenceCityDao
import com.nigtime.weatherapplication.db.service.WishCityDao
import com.nigtime.weatherapplication.domain.repository.ForecastCitiesRepository
import com.nigtime.weatherapplication.domain.repository.PagedSearchRepository
import com.nigtime.weatherapplication.domain.repository.WishCitiesRepository

object CitiesRepositoryFactory {

    fun getForecastCitiesRepository(): ForecastCitiesRepository {
        return ForecastCitiesRepositoryImpl(geWishCityDao(), CityForForecastMapper())
    }

    fun getPagedSearchRepository(): PagedSearchRepository {
        return PagedSearchRepositoryImpl(getReferenceCityDao(), geWishCityDao(), SearchCityMapper())
    }

    fun getWishCitiesRepository(): WishCitiesRepository {
        return WishCitiesRepositoryImpl(getReferenceCityDao(), geWishCityDao(), WishCityMapper())
    }

    private fun geWishCityDao(): WishCityDao {
        return App.INSTANCE.database.wishCityDao()
    }

    private fun getReferenceCityDao(): ReferenceCityDao {
        return App.INSTANCE.database.referenceCityDao()
    }
}