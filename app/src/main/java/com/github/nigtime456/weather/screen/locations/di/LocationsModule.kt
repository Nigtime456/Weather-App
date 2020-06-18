/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.github.nigtime456.weather.screen.locations.di

import com.github.nigtime456.weather.screen.locations.LocationsContract
import dagger.Module
import dagger.Provides

@Module
class LocationsModule(private val view: LocationsContract.View) {

    @Provides
    fun provideView(): LocationsContract.View = view

}