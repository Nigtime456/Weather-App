/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.current.forecast.di

import com.gmail.nigtime456.weatherapplication.di.AppComponent
import com.gmail.nigtime456.weatherapplication.di.FragmentScope
import com.gmail.nigtime456.weatherapplication.ui.screen.current.forecast.CurrentForecastFragment
import dagger.Component

@FragmentScope
@Component(
    modules = [CurrentForecastModule::class, CurrentForecastPresenterModule::class],
    dependencies = [AppComponent::class]
)
interface CurrentForecastComponent {
    fun inject(currentForecastFragment: CurrentForecastFragment)
}