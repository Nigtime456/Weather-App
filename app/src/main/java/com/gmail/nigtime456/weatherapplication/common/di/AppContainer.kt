/*
 * Сreated by Igor Pokrovsky. 2020/5/6
 */

package com.gmail.nigtime456.weatherapplication.common.di

import android.content.Context
import com.gmail.nigtime456.weatherapplication.common.rx.MainSchedulerProvider
import com.gmail.nigtime456.weatherapplication.common.rx.RxAsyncDiffer
import com.gmail.nigtime456.weatherapplication.common.rx.SchedulerProvider
import com.gmail.nigtime456.weatherapplication.domain.repository.ForecastProvider
import com.gmail.nigtime456.weatherapplication.domain.repository.PagedSearchRepository
import com.gmail.nigtime456.weatherapplication.domain.repository.SavedLocationsRepository
import com.gmail.nigtime456.weatherapplication.domain.repository.SettingsProvider
import com.gmail.nigtime456.weatherapplication.net.mappers.*
import com.gmail.nigtime456.weatherapplication.net.repository.ForecastProviderImpl
import com.gmail.nigtime456.weatherapplication.net.repository.ForecastSource
import com.gmail.nigtime456.weatherapplication.net.service.ApiFactory
import com.gmail.nigtime456.weatherapplication.storage.mappers.SavedLocationMapper
import com.gmail.nigtime456.weatherapplication.storage.mappers.SearchCityMapper
import com.gmail.nigtime456.weatherapplication.storage.preference.SettingsProviderImpl
import com.gmail.nigtime456.weatherapplication.storage.repository.PagedSearchRepositoryImpl
import com.gmail.nigtime456.weatherapplication.storage.repository.SavedLocationRepositoryImpl
import com.gmail.nigtime456.weatherapplication.storage.service.ReferenceCitiesDao
import com.gmail.nigtime456.weatherapplication.storage.service.ReferenceCitiesDatabase
import com.gmail.nigtime456.weatherapplication.storage.service.SavedLocationsDao
import com.gmail.nigtime456.weatherapplication.storage.service.SavedLocationsDatabase
import com.gmail.nigtime456.weatherapplication.testing.FakeForecastSource

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

    val schedulerProvider: SchedulerProvider = MainSchedulerProvider()

    val savedLocationsRepository: SavedLocationsRepository

    val forecastProvider: ForecastProvider

    val settingsProvider: SettingsProvider = SettingsProviderImpl(appContext)

    init {
        val referenceCitiesDatabase = ReferenceCitiesDatabase.getInstance(appContext)
        val savedLocationsDatabase = SavedLocationsDatabase.getInstance(appContext)
        referenceCitiesDao = referenceCitiesDatabase.referenceCitiesDao()
        savedLocationsDao = savedLocationsDatabase.savedLocationsDao()

        savedLocationsRepository = getSavedLocationsRepository()

        forecastProvider = ForecastProviderImpl(
            schedulerProvider,
            netSource,
            getCurrentForecastMapper(),
            getHourlyForecastMapper(),
            getDailyForecastMapper()
        )
    }

    private fun getDailyForecastMapper() = DailyForecastMapper(DailyWeatherMapper())

    private fun getHourlyForecastMapper() = HourlyForecastMapper(HourlyWeatherMapper())

    private fun getCurrentForecastMapper(): CurrentForecastMapper {
        return CurrentForecastMapper(
            WindMapper(),
            AirQualityMapper(),
            UvIndexMapper(),
            SunInfoMapper()
        )
    }

    private fun getSavedLocationsRepository(): SavedLocationRepositoryImpl {
        return SavedLocationRepositoryImpl(
            schedulerProvider,
            referenceCitiesDao,
            savedLocationsDao,
            SavedLocationMapper()
        )
    }

    fun getRxAsyncDiffer(): RxAsyncDiffer = RxAsyncDiffer(schedulerProvider)

    fun getPagedSearchRepository(): PagedSearchRepository {
        return PagedSearchRepositoryImpl(
            schedulerProvider,
            referenceCitiesDao,
            savedLocationsDao,
            SearchCityMapper()
        )
    }
}