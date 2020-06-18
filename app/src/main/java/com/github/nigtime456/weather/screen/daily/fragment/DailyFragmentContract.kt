/*
 * Сreated by Igor Pokrovsky. 2020/6/16
 */

package com.github.nigtime456.weather.screen.daily.fragment

import com.github.nigtime456.weather.data.settings.UnitOfLength
import com.github.nigtime456.weather.data.settings.UnitOfPressure
import com.github.nigtime456.weather.data.settings.UnitOfSpeed
import com.github.nigtime456.weather.data.settings.UnitOfTemp
import com.github.nigtime456.weather.screen.base.BasePresenter

interface DailyFragmentContract {

    interface View {
        fun showWeather(
            weatherCode: String,
            highTemp: Double,
            lowTemp: Double,
            unitOfTemp: UnitOfTemp
        )

        fun showWind(
            degrees: Int,
            speed: Double,
            gust: Double,
            unitOfSpeed: UnitOfSpeed
        )

        fun showHumidity(humidity: Double)

        fun showUvIndex(index: Int)

        fun showPressure(
            pressure: Double,
            unitOfPressure: UnitOfPressure
        )

        fun showVisibility(
            visibility: Double,
            unitOfLength: UnitOfLength
        )

        fun showDewPoint(
            dewPoint: Double,
            unitOfTemp: UnitOfTemp
        )

        fun showCloudsCoverage(clouds: Double)
        fun showMoonPhase(phase: Double)
        fun showSunriseAndSunset(sunrise: Long, sunset: Long, timeZone: String)
    }

    interface Presenter : BasePresenter {
        fun start()
    }
}