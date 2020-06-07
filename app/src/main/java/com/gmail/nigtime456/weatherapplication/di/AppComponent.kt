/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.di

import android.content.Context
import com.gmail.nigtime456.weatherapplication.data.repository.ForecastProvider
import com.gmail.nigtime456.weatherapplication.data.repository.LocationsRepository
import com.gmail.nigtime456.weatherapplication.data.repository.SearchRepository
import com.gmail.nigtime456.weatherapplication.data.repository.SettingsProvider
import com.gmail.nigtime456.weatherapplication.di.net.NetworkModule
import com.gmail.nigtime456.weatherapplication.di.other.ContextModule
import com.gmail.nigtime456.weatherapplication.di.other.RxModule
import com.gmail.nigtime456.weatherapplication.di.storage.DatabasesModule
import com.gmail.nigtime456.weatherapplication.di.storage.SettingsProviderModule
import com.gmail.nigtime456.weatherapplication.di.storage.StorageRepositoriesModule
import com.gmail.nigtime456.weatherapplication.tools.rx.SchedulerProvider
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        NetworkModule::class,
        ContextModule::class,
        RxModule::class,
        DatabasesModule::class,
        SettingsProviderModule::class,
        StorageRepositoriesModule::class]
)
interface AppComponent {
    fun getApplicationContext(): Context
    fun getSchedulerProvider(): SchedulerProvider
    fun getSavedLocationsRepository(): LocationsRepository
    fun getPagedSearchRepository(): SearchRepository
    fun getSettingsProvider(): SettingsProvider
    fun getForecastProvider(): ForecastProvider
}