/*
 * Сreated by Igor Pokrovsky. 2020/5/26
 */

package com.gmail.nigtime456.weatherapplication.ui.screen.current.forecast

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.domain.forecast.*
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.domain.settings.UnitOfLength
import com.gmail.nigtime456.weatherapplication.domain.settings.UnitOfPressure
import com.gmail.nigtime456.weatherapplication.domain.settings.UnitOfSpeed
import com.gmail.nigtime456.weatherapplication.domain.settings.UnitOfTemp
import com.gmail.nigtime456.weatherapplication.ui.screen.base.BasePresenter

interface CurrentForecastContract {
    companion object {
        const val DISPLAY_5_DAYS = R.id.currentForecastDisplay5days
        const val DISPLAY_10_DAYS = R.id.currentForecastDisplay10days
        const val DISPLAY_16_DAYS = R.id.currentForecastDisplay16days
    }

    interface View {
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
            hourlyWeatherList: List<HourlyWeather>,
            unitOfTemp: UnitOfTemp
        )

        fun setDailyForecast(
            dailyWeather: List<DailyWeather>,
            unitOfTemp: UnitOfTemp
        )

        fun setWind(wind: Wind, unitOfSpeed: UnitOfSpeed)
        fun setHumidity(humidity: Int)
        fun setPressure(pressure: Double, unitOfPressure: UnitOfPressure)

        // fun setPrecipitation(probabilityOfPrecipitation: ProbabilityOfPrecipitation)
        fun setVisibility(visibility: Double, unitOfLength: UnitOfLength)
        fun setAirQuality(airQuality: AirQuality)
        fun setUvIndex(uvIndex: UvIndex)
        fun setClouds(clouds: Int)

        fun setSunInfo(sunInfo: SunInfo)

        fun showDailyForecastScreen(location: SavedLocation, dayIndex: Int)
    }

    interface Presenter :
        BasePresenter<View> {
        fun viewReady()
        fun changeDisplayedDays(buttonId: Int)
        fun refreshData()
        fun clickDailyWeatherItem(dayIndex: Int)
        fun changeScroll(scrollY: Int)
    }
}