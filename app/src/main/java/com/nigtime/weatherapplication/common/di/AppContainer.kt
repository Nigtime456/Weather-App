/*
 * Сreated by Igor Pokrovsky. 2020/5/6
 */

package com.nigtime.weatherapplication.common.di

import android.content.Context
import com.nigtime.weatherapplication.common.cache.MemoryCacheForecastSource
import com.nigtime.weatherapplication.common.rx.MainSchedulerProvider
import com.nigtime.weatherapplication.common.rx.SchedulerProvider
import com.nigtime.weatherapplication.common.testing.FakeForecastSource
import com.nigtime.weatherapplication.common.testing.FakeSettingsManager
import com.nigtime.weatherapplication.db.mapper.CityForForecastMapper
import com.nigtime.weatherapplication.db.mapper.SearchCityMapper
import com.nigtime.weatherapplication.db.mapper.WishCityMapper
import com.nigtime.weatherapplication.db.repository.ForecastCitiesRepositoryImpl
import com.nigtime.weatherapplication.db.repository.PagedSearchRepositoryImpl
import com.nigtime.weatherapplication.db.repository.WishCitiesRepositoryImpl
import com.nigtime.weatherapplication.db.service.AppDatabase
import com.nigtime.weatherapplication.db.service.ReferenceCityDao
import com.nigtime.weatherapplication.db.service.WishCityDao
import com.nigtime.weatherapplication.domain.city.ForecastCitiesRepository
import com.nigtime.weatherapplication.domain.city.PagedSearchRepository
import com.nigtime.weatherapplication.domain.city.WishCitiesRepository
import com.nigtime.weatherapplication.domain.forecast.ForecastManager
import com.nigtime.weatherapplication.domain.settings.SettingsManager
import com.nigtime.weatherapplication.net.mappers.CurrentForecastMapper
import com.nigtime.weatherapplication.net.mappers.DailyForecastMapper
import com.nigtime.weatherapplication.net.mappers.HourlyForecastMapper
import com.nigtime.weatherapplication.net.repository.CacheForecastSource
import com.nigtime.weatherapplication.net.repository.ForecastManagerImpl
import com.nigtime.weatherapplication.net.repository.ForecastSource
import com.nigtime.weatherapplication.net.service.ApiFactory

class AppContainer(context: Context) {
    //TODO должно быть private
    val referenceCityDao: ReferenceCityDao
    val wishCityDao: WishCityDao

    val weatherApi = ApiFactory.getInstance().getApi()
    val netSource: ForecastSource = FakeForecastSource()
    val memoryCacheSource: CacheForecastSource = MemoryCacheForecastSource()

    val schedulerProvider: SchedulerProvider = MainSchedulerProvider()

    val forecastCitiesRepository: ForecastCitiesRepository
    val wishCityRepository: WishCitiesRepository
    val pagedSearchRepository: PagedSearchRepository

    val forecastManager: ForecastManager

    val settingsManager: SettingsManager


    init {
        val database = AppDatabase.getInstance(context)
        referenceCityDao = database.referenceCityDao()
        wishCityDao = database.wishCityDao()
        forecastCitiesRepository =
            ForecastCitiesRepositoryImpl(wishCityDao, CityForForecastMapper())
        wishCityRepository =
            WishCitiesRepositoryImpl(referenceCityDao, wishCityDao, WishCityMapper())
        pagedSearchRepository = PagedSearchRepositoryImpl(
            referenceCityDao, wishCityDao,
            SearchCityMapper()
        )

        forecastManager = ForecastManagerImpl(
            netSource,
            memoryCacheSource,
            CurrentForecastMapper(),
            HourlyForecastMapper(),
            DailyForecastMapper()
        )

        settingsManager = FakeSettingsManager(context)

    }


}