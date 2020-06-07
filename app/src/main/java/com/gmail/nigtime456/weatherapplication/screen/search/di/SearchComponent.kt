/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.screen.search.di

import com.gmail.nigtime456.weatherapplication.di.ActivityScope
import com.gmail.nigtime456.weatherapplication.di.AppComponent
import com.gmail.nigtime456.weatherapplication.screen.search.SearchActivity
import dagger.Component

@ActivityScope
@Component(
    modules = [SearchModule::class, SearchPresenterModule::class],
    dependencies = [AppComponent::class]
)
interface SearchComponent {
    fun inject(searchActivity: SearchActivity)
}