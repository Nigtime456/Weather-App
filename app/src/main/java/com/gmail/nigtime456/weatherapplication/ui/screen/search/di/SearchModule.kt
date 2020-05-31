/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.search.di

import com.gmail.nigtime456.weatherapplication.ui.screen.search.SearchContract
import dagger.Module
import dagger.Provides

@Module
class SearchModule constructor(private val view: SearchContract.View) {
    @Provides
    fun provideView(): SearchContract.View = view
}