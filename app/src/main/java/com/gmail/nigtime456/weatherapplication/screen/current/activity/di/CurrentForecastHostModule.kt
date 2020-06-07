/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.screen.current.activity.di

import com.gmail.nigtime456.weatherapplication.screen.current.activity.CurrentForecastActivityContract
import dagger.Module
import dagger.Provides

@Module
class CurrentForecastHostModule constructor(
    private val view: CurrentForecastActivityContract.View
) {
    @Provides
    fun provideView(): CurrentForecastActivityContract.View = view
}