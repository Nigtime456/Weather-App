/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.github.nigtime456.weather.screen.currently.fragment.di

import com.github.nigtime456.weather.data.location.SavedLocation
import com.github.nigtime456.weather.screen.currently.fragment.CurrentlyFragmentContract
import dagger.Module
import dagger.Provides

@Module
class CurrentlyFragmentModule constructor(
    private val view: CurrentlyFragmentContract.View,
    private val location: SavedLocation
) {

    @Provides
    fun provideView(): CurrentlyFragmentContract.View = view

    @Provides
    fun provideCurrentLocation(): SavedLocation = location

}