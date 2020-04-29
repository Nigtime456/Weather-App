/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.utility.di

import com.nigtime.weatherapplication.App
import com.nigtime.weatherapplication.domain.repository.net.ForecastManager
import com.nigtime.weatherapplication.net.mappers.CurrentForecastMapper
import com.nigtime.weatherapplication.net.service.ForecastManagerImpl
import com.nigtime.weatherapplication.net.service.NetForecastSourceImpl


object ForecastManagerFactory {

    fun getForecastManager(): ForecastManager {
        val weatherApi = App.INSTANCE.apiService.getApi()
        val netSource = NetForecastSourceImpl(weatherApi)
        return ForecastManagerImpl(netSource, CurrentForecastMapper())
    }
}
