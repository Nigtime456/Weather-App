/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.github.nigtime456.weather.screen.search.di

import com.github.nigtime456.weather.di.ActivityScope
import com.github.nigtime456.weather.di.AppComponent
import com.github.nigtime456.weather.screen.search.SearchActivity
import dagger.Component

@ActivityScope
@Component(
    modules = [SearchModule::class, SearchPresenterModule::class],
    dependencies = [AppComponent::class]
)
interface SearchComponent {
    fun inject(activity: SearchActivity)
}