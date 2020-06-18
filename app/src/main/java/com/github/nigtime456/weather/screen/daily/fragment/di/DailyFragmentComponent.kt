/*
 * Сreated by Igor Pokrovsky. 2020/6/16
 */

package com.github.nigtime456.weather.screen.daily.fragment.di

import com.github.nigtime456.weather.di.AppComponent
import com.github.nigtime456.weather.di.FragmentScope
import com.github.nigtime456.weather.screen.daily.fragment.DailyFragment
import dagger.Component

@FragmentScope
@Component(
    modules = [DailyFragmentModule::class, DailyFragmentPresenterModule::class],
    dependencies = [AppComponent::class]
)
interface DailyFragmentComponent {
    fun inject(fragment: DailyFragment)
}