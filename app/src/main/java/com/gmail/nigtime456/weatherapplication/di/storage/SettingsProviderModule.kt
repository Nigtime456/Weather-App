/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.di.storage

import com.gmail.nigtime456.weatherapplication.data.repository.SettingsProvider
import com.gmail.nigtime456.weatherapplication.di.ApplicationScope
import com.gmail.nigtime456.weatherapplication.storage.preference.SettingsProviderImpl
import dagger.Binds
import dagger.Module

@Module
interface SettingsProviderModule {

    @ApplicationScope
    @Binds
    fun provideSettingsProvider(impl: SettingsProviderImpl): SettingsProvider
}