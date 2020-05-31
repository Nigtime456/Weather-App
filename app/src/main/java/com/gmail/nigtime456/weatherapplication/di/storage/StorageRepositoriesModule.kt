/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.di.storage

import com.gmail.nigtime456.weatherapplication.di.ApplicationScope
import com.gmail.nigtime456.weatherapplication.domain.repository.PagedSearchRepository
import com.gmail.nigtime456.weatherapplication.domain.repository.SavedLocationsRepository
import com.gmail.nigtime456.weatherapplication.storage.repository.PagedSearchRepositoryImpl
import com.gmail.nigtime456.weatherapplication.storage.repository.SavedLocationRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface StorageRepositoriesModule {

    @ApplicationScope
    @Binds
    fun provideSavedLocationsRepository(repository: SavedLocationRepositoryImpl): SavedLocationsRepository

    //Должен создаваться каждый раз новый
    @Binds
    fun providePagedSearchRepository(repository: PagedSearchRepositoryImpl): PagedSearchRepository
}