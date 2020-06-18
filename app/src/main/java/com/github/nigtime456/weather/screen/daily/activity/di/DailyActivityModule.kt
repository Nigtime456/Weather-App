/*
 * Сreated by Igor Pokrovsky. 2020/6/16
 */

package com.github.nigtime456.weather.screen.daily.activity.di

import com.github.nigtime456.weather.data.forecast.PartOfDay
import com.github.nigtime456.weather.data.location.SavedLocation
import com.github.nigtime456.weather.screen.daily.activity.DailyActivityContract
import dagger.Module
import dagger.Provides

@Module
class DailyActivityModule(
    private val view: DailyActivityContract.View,
    private val location: SavedLocation,
    private val partOfDay: PartOfDay,
    private val dayIndex: Int
) {

    @Provides
    fun provideView(): DailyActivityContract.View = view

    @Provides
    fun providePartOfDay(): PartOfDay = partOfDay

    @Provides
    fun provideLocation(): SavedLocation = location

    @Provides
    fun provideDayIndex(): Int = dayIndex
}