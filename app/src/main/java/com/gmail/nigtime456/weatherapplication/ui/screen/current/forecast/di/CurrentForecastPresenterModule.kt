/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.current.forecast.di

import com.gmail.nigtime456.weatherapplication.ui.screen.current.forecast.CurrentForecastContract
import com.gmail.nigtime456.weatherapplication.ui.screen.current.forecast.CurrentForecastPresenter
import dagger.Binds
import dagger.Module

@Module
interface CurrentForecastPresenterModule {
    @Binds
    fun providePresenter(presenter: CurrentForecastPresenter): CurrentForecastContract.Presenter
}