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
import com.gmail.nigtime456.weatherapplication.domain.repository.LocationsRepository
import com.gmail.nigtime456.weatherapplication.domain.repository.SearchRepository
import com.gmail.nigtime456.weatherapplication.storage.repository.LocationRepositoryImpl
import com.gmail.nigtime456.weatherapplication.storage.repository.SearchRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface StorageRepositoriesModule {

    @ApplicationScope
    @Binds
    fun provideSavedLocationsRepository(repository: LocationRepositoryImpl): LocationsRepository

    //Должен создаваться каждый раз новый
    @Binds
    fun providePagedSearchRepository(repository: SearchRepositoryImpl): SearchRepository
}