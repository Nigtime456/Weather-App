/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.locations.di

import com.gmail.nigtime456.weatherapplication.di.ActivityScope
import com.gmail.nigtime456.weatherapplication.di.AppComponent
import com.gmail.nigtime456.weatherapplication.ui.screen.locations.LocationsActivity
import dagger.Component

@ActivityScope
@Component(
    modules = [LocationsModule::class, LocationsPresenterModule::class],
    dependencies = [AppComponent::class]
)
interface LocationsComponent {
    fun inject(locationsActivity: LocationsActivity)
}