/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.di.other

import com.gmail.nigtime456.weatherapplication.di.ApplicationScope
import com.gmail.nigtime456.weatherapplication.tools.rx.MainSchedulerProvider
import com.gmail.nigtime456.weatherapplication.tools.rx.SchedulerProvider
import dagger.Binds
import dagger.Module

@Module
interface RxModule {

    @ApplicationScope
    @Binds
    fun provideSchedulerProvider(provider: MainSchedulerProvider): SchedulerProvider
}