/*
 * Сreated by Igor Pokrovsky. 2020/6/16
 */

package com.github.nigtime456.weather.screen.daily.fragment.di

import com.github.nigtime456.weather.screen.daily.fragment.DailyFragmentContract
import com.github.nigtime456.weather.screen.daily.fragment.DailyFragmentPresenter
import dagger.Binds
import dagger.Module

@Module
interface DailyFragmentPresenterModule {
    @Binds
    fun providePresenter(presenter: DailyFragmentPresenter): DailyFragmentContract.Presenter
}