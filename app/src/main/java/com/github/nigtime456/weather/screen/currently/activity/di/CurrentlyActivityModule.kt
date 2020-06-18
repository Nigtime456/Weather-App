/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.github.nigtime456.weather.screen.currently.activity.di

import com.github.nigtime456.weather.screen.currently.activity.CurrentlyActivityContract
import dagger.Module
import dagger.Provides

@Module
class CurrentlyActivityModule constructor(
    private val view: CurrentlyActivityContract.View
) {
    @Provides
    fun provideView(): CurrentlyActivityContract.View = view
}