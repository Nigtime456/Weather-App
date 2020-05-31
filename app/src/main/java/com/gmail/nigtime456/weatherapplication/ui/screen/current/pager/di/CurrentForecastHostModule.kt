/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.current.pager.di

import com.gmail.nigtime456.weatherapplication.ui.screen.current.pager.CurrentForecastHostContract
import dagger.Module
import dagger.Provides

@Module
class CurrentForecastHostModule constructor(
    private val view: CurrentForecastHostContract.View
) {
    @Provides
    fun provideView(): CurrentForecastHostContract.View = view
}