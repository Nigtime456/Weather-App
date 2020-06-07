/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.di.net

import com.gmail.nigtime456.weatherapplication.data.repository.ForecastProvider
import com.gmail.nigtime456.weatherapplication.di.ApplicationScope
import com.gmail.nigtime456.weatherapplication.mock.FakeForecastSource
import com.gmail.nigtime456.weatherapplication.net.repository.ForecastProviderImpl
import com.gmail.nigtime456.weatherapplication.net.repository.ForecastSource
import dagger.Binds
import dagger.Module

@Module
interface NetworkModule {

    @ApplicationScope
    @Binds
    fun provideForecastProvider(provider: ForecastProviderImpl): ForecastProvider

    @ApplicationScope
    @Binds
    fun provideNetSource(source: FakeForecastSource): ForecastSource
}