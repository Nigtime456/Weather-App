/*
 * Сreated by Igor Pokrovsky. 2020/5/30
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.locations.di

import android.content.Context
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.domain.repository.ForecastProvider
import com.gmail.nigtime456.weatherapplication.domain.repository.SettingsProvider
import com.gmail.nigtime456.weatherapplication.tools.rx.RxDelayedMessageDispatcher
import com.gmail.nigtime456.weatherapplication.tools.rx.SchedulerProvider
import com.gmail.nigtime456.weatherapplication.ui.screen.locations.LocationsContract
import com.gmail.nigtime456.weatherapplication.ui.screen.locations.list.LocationItemPresenterFactory
import dagger.Module
import dagger.Provides

@Module
class LocationsModule(private val view: LocationsContract.View) {

    @Provides
    fun provideView(): LocationsContract.View = view

    @Provides
    fun provideRxMessageDispatcher(
        context: Context,
        schedulerProvider: SchedulerProvider
    ): RxDelayedMessageDispatcher {
        val duration = context.resources.getInteger(R.integer.locations_remove_delay)
        return RxDelayedMessageDispatcher(duration.toLong(), schedulerProvider)
    }

}