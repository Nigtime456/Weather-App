/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.github.nigtime456.weather.di

import android.content.Context
import com.github.nigtime456.weather.data.repository.ForecastProvider
import com.github.nigtime456.weather.data.repository.LocationsRepository
import com.github.nigtime456.weather.data.repository.SearchRepository
import com.github.nigtime456.weather.data.repository.SettingsProvider
import com.github.nigtime456.weather.utils.rx.SchedulerProvider
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        NetModule::class,
        ContextModule::class,
        RxModule::class,
        StorageModule::class]
)
interface AppComponent {
    fun getApplicationContext(): Context
    fun getSchedulerProvider(): SchedulerProvider
    fun getSavedLocationsRepository(): LocationsRepository
    fun getPagedSearchRepository(): SearchRepository
    fun getSettingsProvider(): SettingsProvider
    fun getForecastProvider(): ForecastProvider
}