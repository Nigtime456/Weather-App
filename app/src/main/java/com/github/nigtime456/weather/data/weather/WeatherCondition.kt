/*
 * Сreated by Igor Pokrovsky. 2020/6/17
 */

package com.github.nigtime456.weather.data.weather

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.github.nigtime456.weather.R
import timber.log.Timber

enum class WeatherCondition {
    CLEAR_DAY, CLEAR_NIGHT, RAIN,
    SNOW, SLEET, WIND,
    FOG, CLOUDY, PARTLY_CLOUDY_DAY,
    PARTLY_CLOUDY_NIGHT, HAIL, THUNDERSTORM,
    TORNADO, UNKNOWN;

    @StringRes
    fun getDescription(): Int {
        return when (this) {
            CLEAR_DAY -> R.string.weather_clear_sky
            CLEAR_NIGHT -> R.string.weather_clear_sky
            RAIN -> R.string.weather_rain
            SNOW -> R.string.weather_snow
            SLEET -> R.string.weather_sleet
            WIND -> R.string.weather_wind
            FOG -> R.string.weather_fog
            CLOUDY -> R.string.weather_cloudy
            PARTLY_CLOUDY_DAY -> R.string.weather_partly_cloudy
            PARTLY_CLOUDY_NIGHT -> R.string.weather_partly_cloudy
            HAIL -> R.string.weather_hail
            THUNDERSTORM -> R.string.weather_thunderstorm
            TORNADO -> R.string.weather_tornado
            UNKNOWN -> R.string.stub
        }
    }

    @DrawableRes
    fun getIco(): Int {
        return when (this) {
            CLEAR_DAY -> R.drawable.ic_weather_clear_day
            CLEAR_NIGHT -> R.drawable.ic_weather_clear_night
            RAIN -> R.drawable.ic_weather_rain
            SNOW -> R.drawable.ic_weather_snow
            SLEET -> R.drawable.ic_weather_sleet
            WIND -> R.drawable.ic_weather_wind
            FOG -> R.drawable.ic_weather_fog
            CLOUDY -> R.drawable.ic_weather_cloudy
            PARTLY_CLOUDY_DAY -> R.drawable.ic_weather_partly_cloudy_day
            PARTLY_CLOUDY_NIGHT -> R.drawable.ic_weather_clear_night
            HAIL -> R.drawable.ic_weather_hail
            THUNDERSTORM -> R.drawable.ic_weather_thunderstorm
            TORNADO -> R.drawable.ic_weather_wind
            UNKNOWN -> R.drawable.ic_about_app
        }
    }

    /**
     * https://darksky.net/dev/docs#response-format
     */
    companion object {
        fun fromCode(code: String): WeatherCondition {
            return when (code) {
                "clear-day" -> CLEAR_DAY
                "clear-night" -> CLEAR_NIGHT
                "rain" -> RAIN
                "snow" -> SNOW
                "sleet" -> SLEET
                "wind" -> WIND
                "fog" -> FOG
                "cloudy" -> CLOUDY
                "partly-cloudy-day" -> PARTLY_CLOUDY_DAY
                "partly-cloudy-night" -> CLEAR_NIGHT
                "hail" -> HAIL
                "thunderstorm" -> THUNDERSTORM
                "tornado" -> TORNADO

                else -> {
                    Timber.e("unknown weather code = $code")
                    UNKNOWN
                }
            }
        }
    }
}