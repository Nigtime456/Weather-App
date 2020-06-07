/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.screen.current.activity.di

import com.gmail.nigtime456.weatherapplication.screen.current.activity.CurrentForecastActivityContract
import com.gmail.nigtime456.weatherapplication.screen.current.activity.CurrentForecastActivityPresenter
import dagger.Binds
import dagger.Module

@Module
interface CurrentForecastHostPresenterModule {
    @Binds
    fun providePresenter(presenter: CurrentForecastActivityPresenter): CurrentForecastActivityContract.Presenter
}