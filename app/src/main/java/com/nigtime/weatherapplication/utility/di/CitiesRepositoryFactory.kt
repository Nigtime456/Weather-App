/*
 * Сreated by Igor Pokrovsky. 2020/4/28 
 */

package com.nigtime.weatherapplication.utility.di

import com.nigtime.weatherapplication.App
import com.nigtime.weatherapplication.db.repository.*
import com.nigtime.weatherapplication.db.service.ReferenceCityDao
import com.nigtime.weatherapplication.db.service.WishCityDao
import com.nigtime.weatherapplication.domain.repository.cities.ForecastCitiesRepository
import com.nigtime.weatherapplication.domain.repository.cities.PagedSearchRepository
import com.nigtime.weatherapplication.domain.repository.cities.WishCitiesRepository

object CitiesRepositoryFactory {

    fun getForecastCitiesRepository(): ForecastCitiesRepository {
        return ForecastCitiesRepositoryImpl(getReferenceCityDao(), geWishCityDao())
    }

    fun getPagedSearchRepository(): PagedSearchRepository {
        return PagedSearchRepositoryImpl(getReferenceCityDao(), geWishCityDao())
    }

    fun getWishCitiesRepository(): WishCitiesRepository {
        return WishCitiesRepositoryImpl(getReferenceCityDao(), geWishCityDao())
    }

    private fun geWishCityDao(): WishCityDao {
        return App.INSTANCE.database.wishCityDao()
    }

    private fun getReferenceCityDao(): ReferenceCityDao {
        return App.INSTANCE.database.referenceCityDao()
    }
}