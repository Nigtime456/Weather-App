/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.utility.di

import com.nigtime.weatherapplication.App
import com.nigtime.weatherapplication.domain.repository.ForecastManager
import com.nigtime.weatherapplication.net.mappers.CurrentForecastMapper
import com.nigtime.weatherapplication.net.mappers.DailyForecastMapper
import com.nigtime.weatherapplication.net.mappers.HourlyForecastMapper
import com.nigtime.weatherapplication.net.service.ForecastManagerImpl
import com.nigtime.weatherapplication.net.service.NetForecastSourceImpl
import com.nigtime.weatherapplication.utility.testing.FakeForecastSource


object ForecastManagerFactory {

    fun getForecastManager(): ForecastManager {
        val weatherApi = App.INSTANCE.apiService.getApi()
        val netSource = NetForecastSourceImpl(weatherApi)
        return ForecastManagerImpl(
            FakeForecastSource(),
            CurrentForecastMapper(),
            HourlyForecastMapper(),
            DailyForecastMapper()
        )
    }
}
