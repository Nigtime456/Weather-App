/*
 * Сreated by Igor Pokrovsky. 2020/6/16
 */

package com.github.nigtime456.weather.screen.daily.activity.di

import com.github.nigtime456.weather.di.ActivityScope
import com.github.nigtime456.weather.di.AppComponent
import com.github.nigtime456.weather.screen.daily.activity.DailyActivity
import dagger.Component

@ActivityScope
@Component(
    modules = [DailyActivityModule::class, DailyActivityPresenterModule::class],
    dependencies = [AppComponent::class]
)
interface DailyActivityComponent {
    fun inject(activity: DailyActivity)
}