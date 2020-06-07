/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.screen.locations.di

import com.gmail.nigtime456.weatherapplication.screen.locations.LocationsContract
import dagger.Module
import dagger.Provides

@Module
class LocationsModule(private val view: LocationsContract.View) {

    @Provides
    fun provideView(): LocationsContract.View = view

}