/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

/*
 * Сreated by Igor Pokrovsky. 2020/6/15
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.github.nigtime456.weather.di

import android.content.Context
import com.github.nigtime456.weather.data.repository.LocationsRepository
import com.github.nigtime456.weather.data.repository.SearchRepository
import com.github.nigtime456.weather.data.repository.SettingsProvider
import com.github.nigtime456.weather.storage.preference.SettingsProviderImpl
import com.github.nigtime456.weather.storage.repository.LocationsRepositoryImpl
import com.github.nigtime456.weather.storage.repository.SearchRepositoryImpl
import com.github.nigtime456.weather.storage.service.ReferenceCitiesDao
import com.github.nigtime456.weather.storage.service.ReferenceCitiesDatabase
import com.github.nigtime456.weather.storage.service.SavedLocationsDao
import com.github.nigtime456.weather.storage.service.SavedLocationsDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface StorageModule {

    @ApplicationScope
    @Binds
    fun provideSettingsProvider(impl: SettingsProviderImpl): SettingsProvider

    @ApplicationScope
    @Binds
    fun provideLocationsRepository(repository: LocationsRepositoryImpl): LocationsRepository

    /**
     * Должен создаваться каждый раз новый
     */
    @Binds
    fun provideSearchRepository(repository: SearchRepositoryImpl): SearchRepository

    companion object {

        @ApplicationScope
        @Provides
        @JvmStatic
        fun provideSavedLocationsDao(database: SavedLocationsDatabase): SavedLocationsDao {
            return database.savedLocationsDao()
        }

        @ApplicationScope
        @Provides
        @JvmStatic
        fun providesReferenceCitiesDao(database: ReferenceCitiesDatabase): ReferenceCitiesDao {
            return database.referenceCitiesDao()
        }

        @ApplicationScope
        @Provides
        @JvmStatic
        fun provideSavedLocationsDatabase(context: Context): SavedLocationsDatabase {
            return SavedLocationsDatabase.getInstance(context)
        }

        @ApplicationScope
        @Provides
        @JvmStatic
        fun provideReferenceCitiesDatabase(context: Context): ReferenceCitiesDatabase {
            return ReferenceCitiesDatabase.getInstance(context)
        }
    }
}