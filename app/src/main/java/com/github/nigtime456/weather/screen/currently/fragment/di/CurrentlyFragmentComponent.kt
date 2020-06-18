/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.github.nigtime456.weather.screen.currently.fragment.di

import com.github.nigtime456.weather.di.AppComponent
import com.github.nigtime456.weather.di.FragmentScope
import com.github.nigtime456.weather.screen.currently.fragment.CurrentlyFragment
import dagger.Component

@FragmentScope
@Component(
    modules = [CurrentlyFragmentModule::class, CurrentFragmentPresenterModule::class],
    dependencies = [AppComponent::class]
)
interface CurrentlyFragmentComponent {
    fun inject(fragment: CurrentlyFragment)
}