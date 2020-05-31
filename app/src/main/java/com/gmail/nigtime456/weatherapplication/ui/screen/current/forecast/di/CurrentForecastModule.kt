/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.current.forecast.di

import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.ui.screen.current.forecast.CurrentForecastContract
import dagger.Module
import dagger.Provides
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

@Module
class CurrentForecastModule constructor(
    private val view: CurrentForecastContract.View,
    private val currentLocation: SavedLocation
) {

    private companion object {
        private val DAYS_SWITCH_SUBJECT: Subject<Int> = BehaviorSubject.create()
        private val SCROLL_SUBJECT: Subject<Int> = BehaviorSubject.create()
    }

    @Provides
    fun provideView(): CurrentForecastContract.View = view

    @Provides
    fun provideCurrentLocation(): SavedLocation = currentLocation

    @DaysSwitchSubject
    @Provides
    fun provideDaysSwitchSubject() = DAYS_SWITCH_SUBJECT

    @ScrollSubject
    @Provides
    fun provideScrollSubject() = SCROLL_SUBJECT
}