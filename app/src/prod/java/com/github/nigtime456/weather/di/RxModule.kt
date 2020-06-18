/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.github.nigtime456.weather.di

import com.github.nigtime456.weather.utils.rx.MainSchedulerProvider
import com.github.nigtime456.weather.utils.rx.SchedulerProvider
import dagger.Binds
import dagger.Module

@Module
interface RxModule {

    @ApplicationScope
    @Binds
    fun provideSchedulerProvider(provider: MainSchedulerProvider): SchedulerProvider
}