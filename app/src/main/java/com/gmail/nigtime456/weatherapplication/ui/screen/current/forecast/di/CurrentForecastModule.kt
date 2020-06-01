/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.current.forecast.di

import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.ui.screen.current.forecast.CurrentForecastContract
import dagger.Module
import dagger.Provides
import io.reactivex.subjects.Subject

@Module
class CurrentForecastModule constructor(
    private val view: CurrentForecastContract.View,
    private val currentLocation: SavedLocation,
    private val scrollSubject: Subject<Int>
) {

    @Provides
    fun provideView(): CurrentForecastContract.View = view

    @Provides
    fun provideCurrentLocation(): SavedLocation = currentLocation

    @Provides
    fun providesSyncScrollHelper(): Subject<Int> = scrollSubject

}