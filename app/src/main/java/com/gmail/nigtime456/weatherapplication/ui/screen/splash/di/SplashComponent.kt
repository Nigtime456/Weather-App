/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.splash.di

import com.gmail.nigtime456.weatherapplication.di.ActivityScope
import com.gmail.nigtime456.weatherapplication.di.AppComponent
import com.gmail.nigtime456.weatherapplication.ui.screen.splash.SplashActivity
import dagger.Component


@ActivityScope
@Component(
    modules = [SplashModule::class, SplashPresenterModule::class],
    dependencies = [AppComponent::class]
)
interface SplashComponent {
    fun inject(splashActivity: SplashActivity)
}