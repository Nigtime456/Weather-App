/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.screen.current.fragment.di

import com.gmail.nigtime456.weatherapplication.di.AppComponent
import com.gmail.nigtime456.weatherapplication.di.FragmentScope
import com.gmail.nigtime456.weatherapplication.screen.current.fragment.CurrentForecastFragment
import dagger.Component

@FragmentScope
@Component(
    modules = [CurrentForecastModule::class, CurrentForecastPresenterModule::class],
    dependencies = [AppComponent::class]
)
interface CurrentForecastComponent {
    fun inject(currentForecastFragment: CurrentForecastFragment)
}