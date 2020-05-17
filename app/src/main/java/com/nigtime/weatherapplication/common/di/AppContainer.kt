/*
 * Сreated by Igor Pokrovsky. 2020/5/6
 */

package com.nigtime.weatherapplication.common.di

import android.content.Context
import com.nigtime.weatherapplication.common.cache.MemoryCacheForecastSource
import com.nigtime.weatherapplication.common.rx.MainSchedulerProvider
import com.nigtime.weatherapplication.common.rx.RxAsyncDiffer
import com.nigtime.weatherapplication.common.rx.SchedulerProvider
import com.nigtime.weatherapplication.domain.forecast.ForecastManager
import com.nigtime.weatherapplication.domain.location.PagedSearchRepository
import com.nigtime.weatherapplication.domain.location.SavedLocationsRepository
import com.nigtime.weatherapplication.domain.settings.SettingsManager
import com.nigtime.weatherapplication.net.mappers.CurrentForecastMapper
import com.nigtime.weatherapplication.net.mappers.DailyForecastMapper
import com.nigtime.weatherapplication.net.mappers.HourlyForecastMapper
import com.nigtime.weatherapplication.net.mappers.SunInfoMapper
import com.nigtime.weatherapplication.net.repository.AbstractCacheForecastSource
import com.nigtime.weatherapplication.net.repository.ForecastManagerImpl
import com.nigtime.weatherapplication.net.repository.ForecastSource
import com.nigtime.weatherapplication.net.service.ApiFactory
import com.nigtime.weatherapplication.storage.mapper.SavedLocationMapper
import com.nigtime.weatherapplication.storage.mapper.SearchCityMapper
import com.nigtime.weatherapplication.storage.preference.SettingsManagerImpl
import com.nigtime.weatherapplication.storage.repository.PagedSearchRepositoryImpl
import com.nigtime.weatherapplication.storage.repository.SavedLocationRepositoryImpl
import com.nigtime.weatherapplication.storage.service.AppDatabase
import com.nigtime.weatherapplication.storage.service.ReferenceCitiesDao
import com.nigtime.weatherapplication.storage.service.SavedLocationsDao
import com.nigtime.weatherapplication.testing.FakeForecastSource

/**
 * Контейнер, который содержит ресурсы и объекты, необходимые всему приложению.
 * Для простоты DI они вынесены в отдельный класс, который сохраняется
 * на протяжение всей жизни приложения.
 *
 * @param appContext - контекст приложения.
 */
class AppContainer(val appContext: Context) {

    val referenceCitiesDao: ReferenceCitiesDao
    private val savedLocationsDao: SavedLocationsDao

    private val weatherApi = ApiFactory.getInstance().getApi()
    private val netSource: ForecastSource = FakeForecastSource()
    private val memoryCacheSource: AbstractCacheForecastSource = MemoryCacheForecastSource()

    val schedulerProvider: SchedulerProvider = MainSchedulerProvider()

    val savedLocationsRepository: SavedLocationsRepository

    val forecastManager: ForecastManager

    val settingsManager: SettingsManager = SettingsManagerImpl(appContext)

    init {
        val database = AppDatabase.getInstance(appContext)
        referenceCitiesDao = database.referenceCitiesDao()
        savedLocationsDao = database.savedLocationsDao()


        savedLocationsRepository =
            SavedLocationRepositoryImpl(
                referenceCitiesDao,
                savedLocationsDao,
                SavedLocationMapper()
            )

        forecastManager = ForecastManagerImpl(
            netSource,
            memoryCacheSource,
            CurrentForecastMapper(SunInfoMapper()),
            HourlyForecastMapper(),
            DailyForecastMapper()
        )

    }

    fun getRxAsyncDiffer(): RxAsyncDiffer = RxAsyncDiffer(schedulerProvider)

    fun getPagedSearchRepository(): PagedSearchRepository {
        return PagedSearchRepositoryImpl(
            referenceCitiesDao, savedLocationsDao,
            SearchCityMapper()
        )
    }
}