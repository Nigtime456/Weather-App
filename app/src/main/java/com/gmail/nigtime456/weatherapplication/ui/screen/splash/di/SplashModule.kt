/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.splash.di

import com.gmail.nigtime456.weatherapplication.ui.screen.splash.SplashContract
import dagger.Module
import dagger.Provides


@Module
class SplashModule constructor(private val view: SplashContract.View) {
    @Provides
    fun provideView(): SplashContract.View = view
}