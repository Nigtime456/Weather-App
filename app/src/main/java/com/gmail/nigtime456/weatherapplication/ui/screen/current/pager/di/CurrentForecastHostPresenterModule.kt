/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.current.pager.di

import com.gmail.nigtime456.weatherapplication.ui.screen.current.pager.CurrentForecastHostContract
import com.gmail.nigtime456.weatherapplication.ui.screen.current.pager.CurrentForecastHostPresenter
import dagger.Binds
import dagger.Module

@Module
interface CurrentForecastHostPresenterModule {
    @Binds
    fun providePresenter(presenter: CurrentForecastHostPresenter): CurrentForecastHostContract.Presenter
}