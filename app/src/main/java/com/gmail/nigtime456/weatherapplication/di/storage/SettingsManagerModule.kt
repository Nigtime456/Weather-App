/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.di.storage

import com.gmail.nigtime456.weatherapplication.di.ApplicationScope
import com.gmail.nigtime456.weatherapplication.domain.repository.SettingsManager
import com.gmail.nigtime456.weatherapplication.storage.preference.SettingsManagerImpl
import dagger.Binds
import dagger.Module

@Module
interface SettingsManagerModule {

    @ApplicationScope
    @Binds
    fun provideSettingsProvider(impl: SettingsManagerImpl): SettingsManager
}