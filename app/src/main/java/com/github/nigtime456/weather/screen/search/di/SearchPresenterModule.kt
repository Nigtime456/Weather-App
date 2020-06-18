/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.github.nigtime456.weather.screen.search.di

import com.github.nigtime456.weather.screen.search.SearchContract
import com.github.nigtime456.weather.screen.search.SearchPresenter
import dagger.Binds
import dagger.Module

@Module
interface SearchPresenterModule {
    @Binds
    fun providePresenter(presenter: SearchPresenter): SearchContract.Presenter
}