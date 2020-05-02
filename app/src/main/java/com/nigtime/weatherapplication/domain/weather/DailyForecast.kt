/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.domain.weather

class DailyForecast {
    class DailyWeather(temp: Double, ico: Int) : Weather(temp, ico)
}