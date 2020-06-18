/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.github.nigtime456.weather.screen.splash.di

import com.github.nigtime456.weather.screen.splash.SplashContract
import com.github.nigtime456.weather.screen.splash.SplashPresenter
import dagger.Binds
import dagger.Module

@Module
interface SplashPresenterModule {
    @Binds
    fun providePresenter(presenter: SplashPresenter): SplashContract.Presenter
}