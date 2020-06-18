/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.github.nigtime456.weather.screen.locations.di

import com.github.nigtime456.weather.screen.locations.LocationsContract
import com.github.nigtime456.weather.screen.locations.LocationsPresenter
import dagger.Binds
import dagger.Module

@Module
interface LocationsPresenterModule {
    @Binds
    fun providePresenter(presenter: LocationsPresenter): LocationsContract.Presenter
}