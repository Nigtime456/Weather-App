/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.screen.current.fragment.di

import com.gmail.nigtime456.weatherapplication.screen.current.fragment.CurrentForecastFragmentContract
import com.gmail.nigtime456.weatherapplication.screen.current.fragment.CurrentForecastFragmentPresenter
import dagger.Binds
import dagger.Module

@Module
interface CurrentForecastPresenterModule {
    @Binds
    fun providePresenter(presenter: CurrentForecastFragmentPresenter): CurrentForecastFragmentContract.Presenter
}