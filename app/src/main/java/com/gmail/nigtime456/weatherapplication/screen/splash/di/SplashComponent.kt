/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.screen.splash.di

import com.gmail.nigtime456.weatherapplication.di.ActivityScope
import com.gmail.nigtime456.weatherapplication.di.AppComponent
import com.gmail.nigtime456.weatherapplication.screen.splash.SplashActivity
import dagger.Component


@ActivityScope
@Component(
    modules = [SplashModule::class, SplashPresenterModule::class],
    dependencies = [AppComponent::class]
)
interface SplashComponent {
    fun inject(splashActivity: SplashActivity)
}