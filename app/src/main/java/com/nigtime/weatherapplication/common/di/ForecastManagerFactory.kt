/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.common.di

import com.nigtime.weatherapplication.common.App
import com.nigtime.weatherapplication.common.testing.FakeForecastSource
import com.nigtime.weatherapplication.domain.repository.ForecastManager
import com.nigtime.weatherapplication.net.mappers.CurrentForecastMapper
import com.nigtime.weatherapplication.net.mappers.DailyForecastMapper
import com.nigtime.weatherapplication.net.mappers.HourlyForecastMapper
import com.nigtime.weatherapplication.net.repository.ForecastManagerImpl
import com.nigtime.weatherapplication.net.repository.NetForecastSourceImpl


object ForecastManagerFactory {

    fun getForecastManager(): ForecastManager {
        val weatherApi = App.INSTANCE.apiFactory.getApi()
        val netSource =
            NetForecastSourceImpl(
                weatherApi
            )
        return ForecastManagerImpl(
            FakeForecastSource(),
            App.INSTANCE.memoryCacheSource,
            CurrentForecastMapper(),
            HourlyForecastMapper(),
            DailyForecastMapper()
        )
    }
}
