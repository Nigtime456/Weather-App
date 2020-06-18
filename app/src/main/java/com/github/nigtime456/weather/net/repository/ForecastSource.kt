/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

package com.github.nigtime456.weather.net.repository

import com.github.nigtime456.weather.data.location.SavedLocation
import com.github.nigtime456.weather.net.dto.CachedData

interface ForecastSource {
    fun getForecastAsJson(location: SavedLocation): CachedData
}