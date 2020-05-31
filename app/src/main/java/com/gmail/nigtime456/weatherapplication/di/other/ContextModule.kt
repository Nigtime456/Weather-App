/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.di.other

import android.content.Context
import com.gmail.nigtime456.weatherapplication.di.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class ContextModule constructor(private val appContext: Context) {

    @ApplicationScope
    @Provides
    fun provideContext() : Context = appContext
}