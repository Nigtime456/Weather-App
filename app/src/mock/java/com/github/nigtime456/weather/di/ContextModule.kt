/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.github.nigtime456.weather.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ContextModule constructor(private val appContext: Context) {

    @ApplicationScope
    @Provides
    fun provideContext(): Context = appContext
}