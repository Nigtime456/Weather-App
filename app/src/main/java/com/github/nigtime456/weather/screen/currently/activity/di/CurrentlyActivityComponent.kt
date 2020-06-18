/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.github.nigtime456.weather.screen.currently.activity.di

import com.github.nigtime456.weather.di.ActivityScope
import com.github.nigtime456.weather.di.AppComponent
import com.github.nigtime456.weather.screen.currently.activity.CurrentlyActivity
import dagger.Component

@ActivityScope
@Component(
    modules = [CurrentlyActivityModule::class, CurrentlyActivityPresenterModule::class],
    dependencies = [AppComponent::class]
)
interface CurrentlyActivityComponent {
    fun inject(activity: CurrentlyActivity)
}