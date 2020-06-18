/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.github.nigtime456.weather.screen.locations.di

import com.github.nigtime456.weather.di.ActivityScope
import com.github.nigtime456.weather.di.AppComponent
import com.github.nigtime456.weather.screen.locations.LocationsActivity
import dagger.Component

@ActivityScope
@Component(
    modules = [LocationsModule::class, LocationsPresenterModule::class],
    dependencies = [AppComponent::class]
)
interface LocationsComponent {
    fun inject(activity: LocationsActivity)
}