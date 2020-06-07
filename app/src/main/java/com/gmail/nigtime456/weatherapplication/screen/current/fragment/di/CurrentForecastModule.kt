/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.screen.current.fragment.di

import com.gmail.nigtime456.weatherapplication.data.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.screen.current.fragment.CurrentForecastFragmentContract
import dagger.Module
import dagger.Provides

@Module
class CurrentForecastModule constructor(
    private val view: CurrentForecastFragmentContract.View,
    private val currentLocation: SavedLocation
) {

    @Provides
    fun provideView(): CurrentForecastFragmentContract.View = view

    @Provides
    fun provideCurrentLocation(): SavedLocation = currentLocation

}