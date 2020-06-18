/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.github.nigtime456.weather.screen.splash.di

import com.github.nigtime456.weather.screen.splash.SplashContract
import dagger.Module
import dagger.Provides

@Module
class SplashModule constructor(private val view: SplashContract.View) {
    @Provides
    fun provideView(): SplashContract.View = view
}