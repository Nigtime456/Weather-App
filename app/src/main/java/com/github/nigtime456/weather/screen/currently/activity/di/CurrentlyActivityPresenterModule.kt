/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.github.nigtime456.weather.screen.currently.activity.di

import com.github.nigtime456.weather.screen.currently.activity.CurrentlyActivityContract
import com.github.nigtime456.weather.screen.currently.activity.CurrentlyActivityPresenter
import dagger.Binds
import dagger.Module

@Module
interface CurrentlyActivityPresenterModule {
    @Binds
    fun providePresenter(presenter: CurrentlyActivityPresenter): CurrentlyActivityContract.Presenter
}