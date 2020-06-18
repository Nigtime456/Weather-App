/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.github.nigtime456.weather.screen.splash.di

import com.github.nigtime456.weather.di.ActivityScope
import com.github.nigtime456.weather.di.AppComponent
import com.github.nigtime456.weather.screen.splash.SplashActivity
import dagger.Component

@ActivityScope
@Component(
    modules = [SplashModule::class, SplashPresenterModule::class],
    dependencies = [AppComponent::class]
)
interface SplashComponent {
    fun inject(splashActivity: SplashActivity)
}