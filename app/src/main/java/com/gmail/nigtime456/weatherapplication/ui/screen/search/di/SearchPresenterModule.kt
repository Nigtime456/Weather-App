/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.search.di

import com.gmail.nigtime456.weatherapplication.ui.screen.search.SearchCityPresenter
import com.gmail.nigtime456.weatherapplication.ui.screen.search.SearchContract
import dagger.Binds
import dagger.Module

@Module
interface SearchPresenterModule {
    @Binds
    fun providePresenter(presenter: SearchCityPresenter): SearchContract.Presenter
}