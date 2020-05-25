/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/25
 */

/*
 * Сreated by Igor Pokrovsky. 2020/5/10
 */


package com.gmail.nigtime456.weatherapplication.net.mappers

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.gmail.nigtime456.weatherapplication.R

/**
 * https://www.weatherbit.io/api/codes
 */

object WeatherConditionHelper {

    @StringRes
    fun getDescriptionByCode(code: Int): Int {
        return when (code) {
            200 -> R.string.weather_description_200
            201 -> R.string.weather_description_201
            202 -> R.string.weather_description_202
            230 -> R.string.weather_description_230
            231 -> R.string.weather_description_231
            232 -> R.string.weather_description_232
            233 -> R.string.weather_description_233
            300 -> R.string.weather_description_300
            301 -> R.string.weather_description_301
            302 -> R.string.weather_description_302
            500 -> R.string.weather_description_500
            501 -> R.string.weather_description_501
            502 -> R.string.weather_description_502
            511 -> R.string.weather_description_511
            520 -> R.string.weather_description_520
            521 -> R.string.weather_description_521
            522 -> R.string.weather_description_522
            600 -> R.string.weather_description_600
            601 -> R.string.weather_description_601
            602 -> R.string.weather_description_602
            610 -> R.string.weather_description_610
            611 -> R.string.weather_description_611
            612 -> R.string.weather_description_612
            621 -> R.string.weather_description_621
            622 -> R.string.weather_description_622
            623 -> R.string.weather_description_623
            700 -> R.string.weather_description_700
            711 -> R.string.weather_description_711
            721 -> R.string.weather_description_721
            731 -> R.string.weather_description_731
            741 -> R.string.weather_description_741
            751 -> R.string.weather_description_751
            800 -> R.string.weather_description_800
            801 -> R.string.weather_description_801
            802 -> R.string.weather_description_802
            803 -> R.string.weather_description_803
            804 -> R.string.weather_description_804
            900 -> R.string.weather_description_900
            else -> R.string.unknown_code
        }
    }

    /**
    t01,t02, t03 - thunderstorm with rain
    t04,t05 = Thunderstorm with drizzle
    d01,d02,d03 = drizzle
    r01,r02,f01,r04, u00 - rain
    r03 - heavy rain
    r05, r06 - shower raing-
    s01, s04 - snow
    s02, s03, s06 - heavy snow
    s05 - sleet
    a01, a02, a03, a04, a05, a06 - fog
    c01 - clear sky
    c02 - few clouds
    c03 - broken clouds
    c04 - overcast clouds
     */
    @DrawableRes
    fun getIconByCode(code: String): Int {

        return when (code) {
            "t01d", "t02d", "t03d" -> R.drawable.ic_weather_thunderstorm_with_rain_d
            "t01n", "t02n", "t03n" -> R.drawable.ic_weather_thunderstrom_with_rain_n

            "t04d", "t04n",
            "t05d", "t05n" -> R.drawable.ic_weather_thunderstorm_with_drizzle_dn

            "d01d", "d01n",
            "d02d", "d02n",
            "d03d", "d03n" -> R.drawable.ic_weather_drizzle_dn

            "r01d", "r01n",
            "r02d", "r02n",
            "r04d", "r04n",
            "f01d", "f01n",
            "u00d", "u00n" -> R.drawable.ic_weather_rain_dn

            "r03d", "r03n" -> R.drawable.ic_weather_heavy_rain_dn

            "r05n", "r06n" -> R.drawable.ic_weather_shower_rain_n
            "r05d", "r06d" -> R.drawable.ic_weather_shower_rain_d

            "s01d", "s04d" -> R.drawable.ic_weather_snow_d
            "s01n", "s04n" -> R.drawable.ic_weather_snow_n

            "s02d", "s02n",
            "s03d", "s03n",
            "s06d", "s06n" -> R.drawable.ic_weather_heavy_snow_dn

            "s05d", "s05n" -> R.drawable.ic_weather_sleet_dn

            "a01d", "a02d", "a03d", "a04d", "a05d", "a06d" -> R.drawable.ic_weather_fog_d
            "a01n", "a02n", "a03n", "a04n", "a05n", "a06n" -> R.drawable.ic_weather_fog_n

            "c01d" -> R.drawable.ic_weather_clear_sky_d
            "c01n" -> R.drawable.ic_weather_clear_sky_n

            "c02d" -> R.drawable.ic_weather_few_clouds_d
            "c02n" -> R.drawable.ic_weather_few_clouds_n

            "c03d" -> R.drawable.ic_weather_broken_clouds_d
            "c03n" -> R.drawable.ic_weather_broken_clouds_n

            "c04d", "c04n" -> R.drawable.ic_weather_overcast_clouds_dn

            else -> R.drawable._weather_stub
        }
    }
}