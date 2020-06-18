/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.github.nigtime456.weather.screen.currently.fragment.di

import com.github.nigtime456.weather.screen.currently.fragment.CurrentlyFragmentContract
import com.github.nigtime456.weather.screen.currently.fragment.CurrentlyFragmentPresenter
import dagger.Binds
import dagger.Module

@Module
interface CurrentFragmentPresenterModule {
    @Binds
    fun providePresenter(presenter: CurrentlyFragmentPresenter): CurrentlyFragmentContract.Presenter
}