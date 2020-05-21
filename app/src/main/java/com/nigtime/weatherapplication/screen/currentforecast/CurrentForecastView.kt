/*
 * Сreated by Igor Pokrovsky. 2020/4/27
 */

package com.nigtime.weatherapplication.screen.currentforecast

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.domain.forecast.*
import com.nigtime.weatherapplication.domain.settings.UnitOfLength
import com.nigtime.weatherapplication.domain.settings.UnitOfPressure
import com.nigtime.weatherapplication.domain.settings.UnitOfSpeed
import com.nigtime.weatherapplication.domain.settings.UnitOfTemp

interface CurrentForecastView {
    companion object {
        const val DISPLAY_5_DAYS = R.id.currentForecastDisplay5days
        const val DISPLAY_10_DAYS = R.id.currentForecastDisplay10days
        const val DISPLAY_16_DAYS = R.id.currentForecastDisplay16days
    }

    fun setLocationName(name: String)
    fun setTimezone(timeZone: String)

    fun showLoadLayout()
    fun showErrorLayout()
    fun showErrorMessage()
    fun showMainLayout()
    fun showClockWidget()

    fun disableRefreshLayout()
    fun enableRefreshLayout()
    fun stopRefreshing()

    fun setDisplayedDays(count: Int)
    fun selectDaysSwitchButton(buttonId: Int)
    fun setVerticalScroll(scrollY: Int)


    fun setCurrentTemp(temp: Double, unitOfTemp: UnitOfTemp)
    fun setCurrentFeelsLikeTemp(temp: Double, unitOfTemp: UnitOfTemp)
    fun setCurrentIco(@DrawableRes ico: Int)
    fun setCurrentWeatherDescription(@StringRes description: Int)

    fun setHourlyForecast(
        hourlyWeatherList: List<HourlyForecast.HourlyWeather>,
        unitOfTemp: UnitOfTemp
    )

    fun setDailyForecast(
        dailyWeather: List<DailyForecast.DailyWeather>,
        unitOfTemp: UnitOfTemp
    )

    fun setWind(wind: Wind, unitOfSpeed: UnitOfSpeed)
    fun setHumidity(humidity: Int)
    fun setPressure(pressure: Double, unitOfPressure: UnitOfPressure)
    fun setPrecipitation(probabilityOfPrecipitation: HourlyForecast.ProbabilityOfPrecipitation)
    fun setVisibility(visibility: Double, unitOfLength: UnitOfLength)
    fun setAirQuality(airQuality: AirQuality)
    fun setUvIndex(uvIndex: UvIndex)
    fun setClouds(clouds: Int)

    fun setSunInfo(sunInfo: SunInfo)

}