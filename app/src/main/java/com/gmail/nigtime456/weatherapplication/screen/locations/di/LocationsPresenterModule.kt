/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.screen.locations.di

import com.gmail.nigtime456.weatherapplication.screen.locations.LocationsContract
import com.gmail.nigtime456.weatherapplication.screen.locations.LocationsPresenter
import dagger.Binds
import dagger.Module

@Module
interface LocationsPresenterModule {
    @Binds
    fun providePresenter(presenter: LocationsPresenter): LocationsContract.Presenter
}