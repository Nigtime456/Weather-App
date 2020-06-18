/*
 * Сreated by Igor Pokrovsky. 2020/6/16
 */

package com.github.nigtime456.weather.screen.daily.fragment.di

import com.github.nigtime456.weather.data.forecast.DailyForecast
import com.github.nigtime456.weather.screen.daily.fragment.DailyFragmentContract
import dagger.Module
import dagger.Provides

@Module
class DailyFragmentModule constructor(
    private val view: DailyFragmentContract.View,
    private val forecast: DailyForecast
) {
    @Provides
    fun provideView(): DailyFragmentContract.View = view

    @Provides
    fun provideForecast(): DailyForecast = forecast
}