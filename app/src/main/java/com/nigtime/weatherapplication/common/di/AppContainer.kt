/*
 * Сreated by Igor Pokrovsky. 2020/5/6
 */

package com.nigtime.weatherapplication.common.di

import android.content.Context
import com.nigtime.weatherapplication.common.cache.MemoryCacheForecastSource
import com.nigtime.weatherapplication.common.rx.MainSchedulerProvider
import com.nigtime.weatherapplication.common.rx.RxAsyncDiffer
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
import com.nigtime.weatherapplication.net.mappers.SunInfoMapper
import com.nigtime.weatherapplication.net.repository.AbstractCacheForecastSource
import com.nigtime.weatherapplication.net.repository.ForecastManagerImpl
import com.nigtime.weatherapplication.net.repository.ForecastSource
import com.nigtime.weatherapplication.net.service.ApiFactory

/**
 * Контейнер, который содержит ресурсы и объекты, необходимые всему приложению.
 * Для простоты DI они вынесены в отдельный класс, который сохраняется
 * на протяжение всей жизни приложения.
 *
 * @param appContext - контекст приложения.
 */
class AppContainer(val appContext: Context) {

    val referenceCityDao: ReferenceCityDao
    val wishCityDao: WishCityDao

    private val weatherApi = ApiFactory.getInstance().getApi()
    private val netSource: ForecastSource = FakeForecastSource()
    private val memoryCacheSource: AbstractCacheForecastSource = MemoryCacheForecastSource()

    val schedulerProvider: SchedulerProvider = MainSchedulerProvider()

    val forecastCitiesRepository: ForecastCitiesRepository
    val wishCityRepository: WishCitiesRepository

    val forecastManager: ForecastManager

    val settingsManager: SettingsManager


    init {
        val database = AppDatabase.getInstance(appContext)
        referenceCityDao = database.referenceCityDao()
        wishCityDao = database.wishCityDao()
        forecastCitiesRepository =
            ForecastCitiesRepositoryImpl(wishCityDao, CityForForecastMapper())
        wishCityRepository =
            WishCitiesRepositoryImpl(referenceCityDao, wishCityDao, WishCityMapper())

        forecastManager = ForecastManagerImpl(
            netSource,
            memoryCacheSource,
            CurrentForecastMapper(SunInfoMapper()),
            HourlyForecastMapper(),
            DailyForecastMapper()
        )

        settingsManager = FakeSettingsManager(appContext)


    }

    fun getRxAsyncDiffer() = RxAsyncDiffer(schedulerProvider)

    fun getPagedSearchRepository(): PagedSearchRepository {
        return PagedSearchRepositoryImpl(
            referenceCityDao, wishCityDao,
            SearchCityMapper()
        )
    }


}