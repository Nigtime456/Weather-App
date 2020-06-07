/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.screen.current.activity.di

import com.gmail.nigtime456.weatherapplication.di.ActivityScope
import com.gmail.nigtime456.weatherapplication.di.AppComponent
import com.gmail.nigtime456.weatherapplication.screen.current.activity.CurrentForecastActivity
import dagger.Component

@ActivityScope
@Component(
    modules = [CurrentForecastHostModule::class, CurrentForecastHostPresenterModule::class],
    dependencies = [AppComponent::class]
)
interface CurrentForecastHostComponent {
    fun inject(currentForecastActivity: CurrentForecastActivity)
}