/*
 * Сreated by Igor Pokrovsky. 2020/6/16
 */

package com.github.nigtime456.weather.screen.daily.activity.di

import com.github.nigtime456.weather.screen.daily.activity.DailyActivityContract
import com.github.nigtime456.weather.screen.daily.activity.DailyActivityPresenter
import dagger.Binds
import dagger.Module

@Module
interface DailyActivityPresenterModule {
    @Binds
    fun providePresenter(presenter: DailyActivityPresenter): DailyActivityContract.Presenter
}